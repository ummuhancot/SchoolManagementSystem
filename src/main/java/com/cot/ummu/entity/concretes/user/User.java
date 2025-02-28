package com.cot.ummu.entity.concretes.user;

import com.cot.ummu.entity.concretes.business.LessonProgram;
import com.cot.ummu.entity.concretes.business.Meet;
import com.cot.ummu.entity.concretes.business.StudentInfo;
import com.cot.ummu.entity.enums.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
//control+space yani boşluk bilgi almak icin 

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="t_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true)
    private String username;

    @Column(unique=true)
    private String ssn;

    private String name;

    private String surname;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    private String birthplace;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(unique=true)
    private String phoneNumber;

    @Column(unique=true)
    private String email;

    private Boolean buildIn;
    private String motherName;
    private String fatherName;
    private int studentNumber;
    private boolean isActive;
    private Boolean isAdvisor;
    private Long advisorTeacherId;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @OneToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private UserRole userRole;

    @OneToMany(mappedBy = "teacher",cascade = CascadeType.REMOVE)
    private List<StudentInfo>studentInfos;

    @ManyToMany
    @JoinTable(
            name = "user_lessonProgram",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "lesson_program_id")
    )
    private List<LessonProgram>lessonProgramList;


    @JsonIgnore
    @ManyToMany(mappedBy = "studentList")
    private List<Meet>meetList;




}
