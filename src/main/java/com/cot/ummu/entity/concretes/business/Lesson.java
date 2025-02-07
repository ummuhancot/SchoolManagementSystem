package com.cot.ummu.entity.concretes.business;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String lessonName;

    private Integer creditScore;

    private Boolean inCompulsory;

    @JsonIgnore
    @ManyToMany(mappedBy = "lessons")//,cascade = CascadeType.REMOVE kaldırdık.
    private Set<LessonProgram> lessonPrograms;





}
