package com.cot.ummu.service.businnes;

import com.cot.ummu.entity.concretes.business.EducationTerm;
import com.cot.ummu.entity.concretes.business.Lesson;
import com.cot.ummu.payload.request.bussines.LessonProgramRequest;
import com.cot.ummu.payload.response.businnes.LessonProgramResponse;
import com.cot.ummu.payload.response.businnes.ResponseMessage;
import com.cot.ummu.repository.businnes.LessonProgramRepository;
import com.cot.ummu.service.validator.TimeValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class LessonProgramService {

    private final LessonProgramRepository lessonProgramRepository;
    private final LessonService lessonService;
    private final EducationTermService educationTermService;
    private final TimeValidator timeValidator;

    public ResponseMessage<LessonProgramResponse> saveLessonProgram(
            @Valid LessonProgramRequest lessonProgramRequest) {
        //get lessons from DB
        Set<Lesson>lessons = lessonService.getAllByIdSet(lessonProgramRequest.getLessonIdList());
        //get education term from DB
        EducationTerm educationTerm = educationTermService.isEducationTermExist(lessonProgramRequest.getEducationTermId());
        //validate start + end time
        timeValidator.checkStartIsBeforeStop(
                lessonProgramRequest.getStartTime(),lessonProgramRequest.getStopTime());
        //mapping
        return null;
    }
}
