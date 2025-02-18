package com.cot.ummu.service.businnes;

import com.cot.ummu.entity.concretes.business.Lesson;
import com.cot.ummu.exception.ConflictException;
import com.cot.ummu.payload.mappers.LessonMapper;
import com.cot.ummu.payload.messages.ErrorMessages;
import com.cot.ummu.payload.messages.SuccessMessages;
import com.cot.ummu.payload.request.bussines.LessonRequest;
import com.cot.ummu.payload.response.businnes.LessonResponse;
import com.cot.ummu.payload.response.businnes.ResponseMessage;
import com.cot.ummu.repository.businnes.LessonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
@RequiredArgsConstructor
public class LessonService {

    private final LessonRepository lessonRepository;
    private final LessonMapper lessonMapper;


    public ResponseMessage<LessonResponse> saveLesson(@Valid LessonRequest lessonRequest) {
        //validate - lesson name must be unique
        isLessonExsistByName(lessonRequest.getLessonName());
        //map DTO to Entity
        Lesson lesson = lessonMapper.mapLessonRequestToLesson(lessonRequest);
        Lesson savedLesson = lessonRepository.save(lesson);

        return ResponseMessage.<LessonResponse>builder()
                .returnBody(lessonMapper.mapLessonToLessonResponse(savedLesson))
                .httpStatus(HttpStatus.CREATED)
                .message(SuccessMessages.LESSON_SAVE)
                .build();
    }

    private void isLessonExsistByName(String lessonName){
        if (lessonRepository.findByLessonNameEqualsIgnoreCase(lessonName).isPresent()){
            throw new ConflictException(String.format(ErrorMessages.ALREADY_CREATED_LESSON_MESSAGE,lessonName));
        }
    }



}
