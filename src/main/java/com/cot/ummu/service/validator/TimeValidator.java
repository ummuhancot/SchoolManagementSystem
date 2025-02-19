package com.cot.ummu.service.validator;

import com.cot.ummu.exception.BadRequestException;
import com.cot.ummu.payload.messages.ErrorMessages;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class TimeValidator {

    //validate if start time is before stap time
    public void checkStartIsBeforeStop(LocalTime start,LocalTime stop){
        if (start.isBefore(stop) || start.equals(stop)){
            throw new BadRequestException(ErrorMessages.TIME_NOT_VALID_MESSAGE);
        }
    }



}
