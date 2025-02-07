package com.cot.ummu.entity.concretes.business;

/// control + shft + v copyaladıklarının hepsini gösterir ve 1,2 ,3 gibi tuşlara basarak kopyalar.
import com.cot.ummu.entity.concretes.user.User;
import com.cot.ummu.entity.enums.Day;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LessonProgram {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Day day;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "HH:mm",timezone = "US")
    private LocalTime startTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "HH:mm",timezone = "US")
    private LocalTime stopTime;

    @ManyToMany
    @JoinTable(
            name = "lesson_program_lesson",
            joinColumns = @JoinColumn(name = "lessonprogram_id"),
            inverseJoinColumns = @JoinColumn(name = "lesson_id")
    )
    private Set<Lesson>lessons;

    @ManyToOne
    private EducationTerm educationTerm;

    @JsonIgnore
    @ManyToMany(mappedBy = "lessonProgramList",fetch = FetchType.EAGER)
    private Set<User>users;


    @PreRemove
    private void removeLessonFromUser(){
        users.forEach(user -> user.getLessonProgramList().remove(this));
    }


}
