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
public class StudentInfo {//ögrenci bilgileri

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer absentee;//devamsızlık kac defa gelmemiş

    private Double midtermExam;//ara sınavlar

    private Double finalExam;//final sınavı

    private String infoNote;//bilgi notu

    private Double examAverage;//sınav ortalaması

    @Enumerated(EnumType.STRING)
    private Note letterGrade;//harf Derecesi

    @ManyToOne
    @JsonIgnore
    private User teacher;

    @ManyToOne
    @JsonIgnore
    private User student;

    @ManyToOne
    private Lesson lesson;//ders

    @OneToOne
    private EducationTerm educationTerm;//eğitim Dönemi




}
