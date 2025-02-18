package com.cot.ummu.payload.mappers;

import com.cot.ummu.entity.concretes.business.Lesson;
import com.cot.ummu.payload.request.bussines.LessonRequest;
import com.cot.ummu.payload.response.businnes.LessonResponse;
import org.springframework.stereotype.Component;


@Component
public class LessonMapper {



    public Lesson mapLessonRequestToLesson(LessonRequest lessonRequest){
        return Lesson.builder()
                .lessonName(lessonRequest.getLessonName())
                .creditScore(lessonRequest.getCreditScore())
                .isCompulsory(lessonRequest.getIsCompulsory())
                .build();


    }


    public LessonResponse mapLessonToLessonResponse(Lesson lesson){
        return LessonResponse.builder()
                .lessonId(lesson.getId())
                .lessonName(lesson.getLessonName())
                .creditScore(lesson.getCreditScore())
                .isCompulsory(lesson.getIsCompulsory())
                .build();

    }



}
