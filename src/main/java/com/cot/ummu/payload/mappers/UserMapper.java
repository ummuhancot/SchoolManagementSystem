package com.cot.ummu.payload.mappers;

import com.cot.ummu.entity.concretes.user.User;
import com.cot.ummu.entity.enums.RoleType;
import com.cot.ummu.exception.ResourceNotFoundException;
import com.cot.ummu.payload.messages.ErrorMessages;
import com.cot.ummu.payload.request.user.UserRequest;
import com.cot.ummu.payload.response.user.UserResponse;
import com.cot.ummu.service.user.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class UserMapper {

    /** her method dökamnta ederiz böyle
     * @param userRequest DTO from pastman or FE.
     * @param userRole role of user tobe created or updated
     * @return user entity
     */
    private final UserRoleService userRoleService;

    private final PasswordEncoder passwordEncoder;



    public User mapUserRequestToUser(UserRequest userRequest,String userRole){
        User user = User.builder()
                .username(userRequest.getUsername())
                .name(userRequest.getName())
                .surname(userRequest.getSurname())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .ssn(userRequest.getSsn())
                .birthday(userRequest.getBirthDay())
                .birthplace(userRequest.getBirthPlace())
                .phoneNumber(userRequest.getPhoneNumber())
                .gender(userRequest.getGender())
                .email(userRequest.getEmail())
                .buildIn(userRequest.getBuildIn())
                .isActive(false)
                .build();

                //rol ile user to one relationship e sahip olduğu icin
                //bunu DB den fetch edip requeste eklememiz gerekir.
                if (userRole.equalsIgnoreCase(RoleType.ADMIN.getName())){//eger edminse
                    //eger username ismi Admin ise datalar değiltirilmez
                    if (Objects.equals(userRequest.getUsername(),"Admin")){
                        user.setBuildIn(true);
                    }
                    user.setUserRole(userRoleService.getUserRole(RoleType.ADMIN));

                } else if (userRole.equalsIgnoreCase(RoleType.MANAGER.getName())) {
                    user.setUserRole(userRoleService.getUserRole(RoleType.MANAGER));
                } else if (userRole.equalsIgnoreCase(RoleType.ASSISTANT_MANAGER.getName())) {
                    user.setUserRole(userRoleService.getUserRole(RoleType.ASSISTANT_MANAGER));
                } else if (userRole.equalsIgnoreCase(RoleType.STUDENT.getName())) {
                    user.setUserRole(userRoleService.getUserRole(RoleType.STUDENT));
                } else if (userRole.equalsIgnoreCase(RoleType.TEACHER.getName())) {
                    user.setUserRole(userRoleService.getUserRole(RoleType.TEACHER));
                }else {
                    throw new ResourceNotFoundException(
                            String.format(ErrorMessages.NOT_FOUND_USER_USER_ROLE_MESSAGE,userRole));
                }

                return user;

    }


    public UserResponse mapUserToUserResponse(User user){
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .surname(user.getSurname())
                .phoneNumber(user.getPhoneNumber())
                .gender(user.getGender())
                .birthDay(user.getBirthday())
                .birthPlace(user.getBirthplace())
                .ssn(user.getSsn())
                .email(user.getEmail())
                .userRole(user.getUserRole().getRoleType().name())
                .build();
    }


}
