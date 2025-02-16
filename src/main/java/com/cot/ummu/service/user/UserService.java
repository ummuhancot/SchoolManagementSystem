package com.cot.ummu.service.user;

import com.cot.ummu.entity.concretes.user.User;
import com.cot.ummu.payload.mappers.UserMapper;
import com.cot.ummu.payload.messages.SuccessMessages;
import com.cot.ummu.payload.request.user.UserRequest;
import com.cot.ummu.payload.request.user.UserRequestWithoutPassword;
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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
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

    public ResponseMessage <UserResponse> updateUserById(UserRequest userRequest, Long userId) {
        //validate if user exist
        User userFromDb = methodHelper.isUserExist(userId);
        //build in users can not be updated
        methodHelper.checkBuildIn(userFromDb);
        //validate unique properties
        uniquePropertyValidator.checkUniqueProperty(userFromDb,userRequest);
        //mapping
        User userToSave = userMapper.mapUserRequestToUser(userRequest,userFromDb.getUserRole().getRoleName());
        userToSave.setId(userId);
        User saveUser = userRepository.save(userToSave);
        return ResponseMessage.<UserResponse>builder()
                .message(SuccessMessages.USER_UPDATE)
                .httpStatus(HttpStatus.OK)
                .returnBody(userMapper.mapUserToUserResponse(saveUser))
                .build();
    }

    public String updateLoggedInUser(@Valid UserRequestWithoutPassword userRequestWithoutPassword, HttpServletRequest httpServletRequest) {
        String username= (String) httpServletRequest.getAttribute("username");
        User user = userRepository.findByUsername(username);
        methodHelper.checkBuildIn(user);
        uniquePropertyValidator.checkUniqueProperty(user,userRequestWithoutPassword);
        user.setName(userRequestWithoutPassword.getName());
        user.setSsn(userRequestWithoutPassword.getSsn());
        user.setUsername(userRequestWithoutPassword.getUsername());
        user.setSurname(userRequestWithoutPassword.getSurname());
        user.setBirthday(userRequestWithoutPassword.getBirthDay());
        user.setBirthplace(userRequestWithoutPassword.getBirthPlace());
        user.setEmail(userRequestWithoutPassword.getEmail());
        user.setPhoneNumber(userRequestWithoutPassword.getPhoneNumber());
        user.setGender(userRequestWithoutPassword.getGender());
        userRepository.save(user);
        return SuccessMessages.USER_UPDATE;

    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
}
