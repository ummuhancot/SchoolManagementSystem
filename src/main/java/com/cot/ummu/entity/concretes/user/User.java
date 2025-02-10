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
@Table(name = "t_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String ssn;//sosyal güvenlik numarası

    private String name;

    private String surname;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    private String birthplace;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)//diserilayz edemesin bytara
    private String password;

    @Column(unique = true)
    private String phoneNumber;

    @Column(unique = true)
    private String email;

    private Boolean buildIn;//propertisine değiştirilip değiştiremeyeceğine karar vermek icin kullanıycaz

    private String motherName;

    private String fatherName;

    private int StudentNumber;

    private boolean isActive;

    private boolean isAdvisor;//danışman öğretmenler icin kullanılacak

    private Long advisorTeacherId;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @OneToOne//birtane userin bir tane rolü olur
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    //sadece yazı yazabilirmişsin deserialization edemeyiz ayni byte olarak alırız byte olarak gelir deserilazetion ederek başka objelere ceviririz güvenlik icin olabilir
    //Seçili kod satırı, userRole alanının serileştirme ve serileştirme dışı bırakma davranışını kontrol etmek için Jackson kütüphanesinden @JsonProperty anotasyonunu kullanıyor. access = JsonProperty.Access.READ_ONLY parametresi, bu alanın yalnızca JSON çıktısına (serileştirme) dahil edilmesi gerektiğini ve JSON girdisine (serileştirme dışı bırakma) dahil edilmemesi gerektiğini belirtir. Bu, genellikle güvenlik nedenleriyle, istemcilerin belirli alanları değiştirmesini önlemek için kullanılır.
    private UserRole userRole;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.REMOVE)
    private List<StudentInfo> studentInfos;


    @ManyToMany
      @JoinTable(
          name = "user_lessonProgram",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "lesson_program_id")
      )
      private Set<LessonProgram>lessonProgramList;


    @JsonIgnore
    @ManyToMany(mappedBy = "studentList")
    private List<Meet>meetList;





}