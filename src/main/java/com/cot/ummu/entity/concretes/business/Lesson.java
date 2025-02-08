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
public class Lesson {//ders

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String lessonName;//ders adı

    private Integer creditScore;//kredi Puanı

    private Boolean inCompulsory;//zorunlu

    @JsonIgnore
    @ManyToMany(mappedBy = "lessons")//,cascade = CascadeType.REMOVE kaldırdık.
    private Set<LessonProgram> lessonPrograms;//ders programı




}
