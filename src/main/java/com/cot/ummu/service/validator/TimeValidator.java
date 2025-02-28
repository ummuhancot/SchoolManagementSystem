package com.cot.ummu.service.validator;

import com.cot.ummu.entity.concretes.business.LessonProgram;
import com.cot.ummu.exception.BadRequestException;
import com.cot.ummu.payload.messages.ErrorMessages;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class TimeValidator {

    //validate if start time is before stop time
    public void checkStartIsBeforeStop(LocalTime start, LocalTime stop) {
        if (start.isAfter(stop) || start.equals(stop)){
            throw new BadRequestException(ErrorMessages.TIME_NOT_VALID_MESSAGE);
        }
    }


    public void checkDuplicateLessonProgram(List<LessonProgram> lessonPrograms){
        Set<String> uniqueLessonProgramDays = new HashSet<>();
        Set<LocalTime>existingLessonProgramStartTimes = new HashSet<>();
        Set<LocalTime>existingLessonProgramStopTimes = new HashSet<>();
        for (LessonProgram lessonProgram: lessonPrograms){
            String lessonProgramDay = lessonProgram.getDay().name();
            //check if they are on the same day
            if(uniqueLessonProgramDays.contains(lessonProgramDay)){
                //check related to start time
                for (LocalTime startTime: existingLessonProgramStartTimes){
                    //if start times are the same
                    if(lessonProgram.getStartTime().equals(startTime)){
                        throw new BadRequestException(ErrorMessages.LESSON_PROGRAM_ALREADY_EXIST);
                    }
                    //check if there is a conflict
                    if(lessonProgram.getStartTime().isBefore(startTime) && lessonProgram.getStopTime().isAfter(startTime)){
                        throw new BadRequestException(ErrorMessages.LESSON_PROGRAM_ALREADY_EXIST);
                    }
                    // if stop times are the same
                    for (LocalTime stopTime : existingLessonProgramStopTimes) {
                        if(lessonProgram.getStartTime().isBefore(stopTime) && lessonProgram.getStopTime().isAfter(stopTime)){
                            throw new BadRequestException(ErrorMessages.LESSON_PROGRAM_ALREADY_EXIST);
                        }
                    }
                }
            }
            uniqueLessonProgramDays.add(lessonProgramDay);
            existingLessonProgramStartTimes.add(lessonProgram.getStartTime());
            existingLessonProgramStopTimes.add(lessonProgram.getStopTime());
        }
    }



}
