package com.cot.ummu.payload.response.user;

import com.cot.ummu.entity.concretes.business.LessonProgram;
import com.cot.ummu.payload.response.abstracts.BaseUserResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentResponse extends BaseUserResponse {

    private List<LessonProgram> lessonProgramList;
    private int studentNumber;
    private String motherName;
    private String fatherName;
    private boolean isActive;

}
