package com.cot.ummu.service.validator;

import com.cot.ummu.entity.concretes.user.User;
import com.cot.ummu.exception.ConflictException;
import com.cot.ummu.payload.messages.ErrorMessages;
import com.cot.ummu.payload.request.abstracts.AbstractUserRequest;
import com.cot.ummu.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UniquePropertyValidator {

    private final UserRepository userRepository;

    //update methodu db de başka biri kullanıyor olabilir.
    public void checkUniqueProperty(User user, AbstractUserRequest userRequest){
        String updatesUserName = "";
        String updatesUserSsn = "";
        String updatesUserEmail = "";
        String updatesUserPhone = "";

        boolean isChanged=false;
        //we are checking that if we change the unique prop.s
        if (!user.getUsername().equals(userRequest.getUsername())){
            updatesUserName=userRequest.getUsername();
            isChanged= true;
        }
        if (!user.getSsn().equals(userRequest.getSsn())){
            updatesUserSsn =userRequest.getSsn();
            isChanged=true;
        }

        if (!user.getEmail().equals(userRequest.getEmail())){
            updatesUserEmail =userRequest.getEmail();
            isChanged=true;
        }
        if (!user.getPhoneNumber().equals(userRequest.getPhoneNumber())){
            updatesUserPhone =userRequest.getPhoneNumber();
            isChanged=true;
        }

        if (isChanged){
            checkDuplication(updatesUserName,updatesUserSsn,updatesUserPhone,updatesUserEmail);
        }

    }




    public void checkDuplication(
            String username,
            String ssn,
            String phone,
            String email) {
        if(userRepository.existsByUsername(username)) {
            throw new ConflictException(String.format(ErrorMessages.ALREADY_REGISTER_MESSAGE_USERNAME, username));
        }
        if(userRepository.existsByEmail(email)) {
            throw new ConflictException(String.format(ErrorMessages.ALREADY_REGISTER_MESSAGE_EMAIL, email));
        }
        if(userRepository.existsByPhoneNumber(phone)) {
            throw new ConflictException(String.format(ErrorMessages.ALREADY_REGISTER_MESSAGE_PHONE_NUMBER, phone));
        }
        if(userRepository.existsBySsn(ssn)) {
            throw new ConflictException(String.format(ErrorMessages.ALREADY_REGISTER_MESSAGE_SSN, ssn));
        }

    }

}

