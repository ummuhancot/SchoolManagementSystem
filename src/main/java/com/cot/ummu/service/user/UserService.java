package com.cot.ummu.service.user;

import com.cot.ummu.entity.concretes.user.User;
import com.cot.ummu.payload.mappers.UserMapper;
import com.cot.ummu.payload.messages.SuccessMessages;
import com.cot.ummu.payload.request.user.UserRequest;
import com.cot.ummu.payload.response.abstracts.BaseUserResponse;
import com.cot.ummu.payload.response.businnes.ResponseMessage;
import com.cot.ummu.payload.response.user.UserResponse;
import com.cot.ummu.repository.user.UserRepository;
import com.cot.ummu.service.helpar.MethodHelper;
import com.cot.ummu.service.helpar.PageableHelper;
import com.cot.ummu.service.validator.UniquePropertyValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UniquePropertyValidator uniquePropertyValidator;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final MethodHelper methodHelper;
    private final PageableHelper pageableHelper;


    public ResponseMessage<UserResponse> saveUser(UserRequest userRequest, String userRole) {
        //validate unique prop.
        uniquePropertyValidator.checkDuplication(
                userRequest.getUsername(),
                userRequest.getSsn(),
                userRequest.getPhoneNumber(),
                userRequest.getEmail()
        );
        //DTO->entity mapping
        User userToSave = userMapper.mapUserRequestToUser(userRequest,userRole);
        //save operation
        User savedUser = userRepository.save(userToSave);
        //entity ->DTO mapping
        return ResponseMessage.<UserResponse>builder()
                .message(SuccessMessages.USER_CREATE)
                .returnBody(userMapper.mapUserToUserResponse(savedUser))
                .httpStatus(HttpStatus.OK)
                .build();
    }

    public ResponseMessage<BaseUserResponse> findUserById(Long userId) {
        //validate if user exist in DB
        User user = methodHelper.isUserExist(userId);
        return ResponseMessage.<BaseUserResponse>builder()
                .message(SuccessMessages.USER_FOUND)
                .returnBody(userMapper.mapUserToUserResponse(user))
                .httpStatus(HttpStatus.OK)
                .build();
    }

    public String deleteUserById(Long userId) {
        //validate if user exist in DB
        methodHelper.isUserExist(userId);
        //delete user from DB
        userRepository.deleteById(userId);
        return SuccessMessages.USER_DELETE;
    }

    public Page<UserResponse> getUserByPage(int page, int size, String sort, String type,
                                            String userRole) {
        Pageable pageable = pageableHelper.getPageable(page, size, sort, type);
        return userRepository.findUserByUserRoleQuery(userRole,pageable)
                .map(userMapper::mapUserToUserResponse);
    }
}
