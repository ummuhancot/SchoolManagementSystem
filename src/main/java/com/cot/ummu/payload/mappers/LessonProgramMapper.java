package com.cot.ummu.payload.mappers;


import com.cot.ummu.entity.concretes.business.EducationTerm;
import com.cot.ummu.entity.concretes.business.Lesson;
import com.cot.ummu.entity.concretes.business.LessonProgram;
import com.cot.ummu.payload.request.bussines.LessonProgramRequest;
import com.cot.ummu.payload.response.businnes.LessonProgramResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LessonProgramMapper {


  public LessonProgram mapLessonProgramRequestToLessonProgram(
          LessonProgramRequest lessonProgramRequest,
          List<Lesson> lessonSet, EducationTerm educationTerm) {
    return LessonProgram.builder()
        .startTime(lessonProgramRequest.getStartTime())
        .stopTime(lessonProgramRequest.getStopTime())
        .day(lessonProgramRequest.getDay())
        .lessons(lessonSet)
        .educationTerm(educationTerm)
        .build();
  }

  public LessonProgramResponse mapLessonProgramToLessonProgramResponse(LessonProgram lessonProgram) {
    return LessonProgramResponse.builder()
        .day(lessonProgram.getDay())
        .educationTerm(lessonProgram.getEducationTerm())
        .startTime(lessonProgram.getStartTime())
        .stopTime(lessonProgram.getStopTime())
        .lessonName(lessonProgram.getLessons())
        .lessonProgramId(lessonProgram.getId())
        .build();
  }







}
