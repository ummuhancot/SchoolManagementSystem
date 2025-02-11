package com.cot.ummu.service.helpar;


import com.cot.ummu.entity.concretes.user.User;
import com.cot.ummu.exception.ResourceNotFoundException;
import com.cot.ummu.payload.messages.ErrorMessages;
import com.cot.ummu.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jca.cci.RecordTypeNotSupportedException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MethodHelper {

    private final UserRepository userRepository;


    public User isUserExist(Long id){
        return userRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_USER_MESSAGE, id )));
    }



}
