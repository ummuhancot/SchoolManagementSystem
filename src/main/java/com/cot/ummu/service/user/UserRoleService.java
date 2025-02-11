package com.cot.ummu.service.user;


import com.cot.ummu.entity.concretes.user.UserRole;
import com.cot.ummu.entity.enums.RoleType;
import com.cot.ummu.exception.ResourceNotFoundException;
import com.cot.ummu.payload.messages.ErrorMessages;
import com.cot.ummu.repository.user.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserRoleService {

    private final UserRoleRepository userRoleRepository;

    public UserRole getUserRole(RoleType roleType){
        return userRoleRepository.findByUserRoleType(roleType)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.ROLE_NOT_FOUND));
    }

    public List<UserRole> getAllUserRoles(){
        return userRoleRepository.findAll();
    }



}
