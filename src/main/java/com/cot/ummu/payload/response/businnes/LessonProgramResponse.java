package com.cot.ummu.payload.response.businnes;

import com.cot.ummu.entity.concretes.business.EducationTerm;
import com.cot.ummu.entity.concretes.business.Lesson;
import com.cot.ummu.entity.enums.Day;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LessonProgramResponse {
    private Long lessonProgramId;
    private Day day;
    private LocalTime startTime;
    private LocalTime stopTime;
    private Set<Lesson>lessonName;
    private EducationTerm educationTerm;

}
