package com.cot.ummu.service.user;

import com.cot.ummu.entity.concretes.user.User;
import com.cot.ummu.payload.mappers.UserMapper;
import com.cot.ummu.payload.messages.SuccessMessages;
import com.cot.ummu.payload.request.user.UserRequest;
import com.cot.ummu.payload.response.businnes.ResponseMessage;
import com.cot.ummu.payload.response.user.UserResponse;
import com.cot.ummu.repository.user.UserRepository;
import com.cot.ummu.service.validator.UniquePropertyValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UniquePropertyValidator uniquePropertyValidator;
    private final UserMapper userMapper;
    private final UserRepository userRepository;


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
}
