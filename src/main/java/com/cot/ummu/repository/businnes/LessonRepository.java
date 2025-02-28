package com.cot.ummu.repository.businnes;

import com.cot.ummu.entity.concretes.business.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {

  //@Query("select l from Lesson l where l.lessonName = :lessonName")
  Optional<Lesson> findByLessonNameEqualsIgnoreCase(String lessonName);


}
