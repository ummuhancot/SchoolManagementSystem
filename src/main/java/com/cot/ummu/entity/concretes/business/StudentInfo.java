package com.cot.ummu.entity.concretes.business;


import com.cot.ummu.entity.concretes.user.User;
import com.cot.ummu.entity.enums.Note;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer absentee;//devamsızlık kac defa gelmemiş

    private Double midtermExam;

    private Double finalExam;

    private String infoNote;

    private Double examAverage;

    @Enumerated(EnumType.STRING)
    private Note letterGrade;

    @ManyToOne
    @JsonIgnore
    private User teacher;

    @ManyToOne
    @JsonIgnore
    private User student;

    @ManyToOne
    private Lesson lesson;

    @OneToOne
    private EducationTerm educationTerm;




}
