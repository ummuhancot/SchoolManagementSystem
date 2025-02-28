package com.cot.ummu.service.businnes;


import com.cot.ummu.entity.concretes.business.EducationTerm;
import com.cot.ummu.entity.concretes.business.Lesson;
import com.cot.ummu.entity.concretes.business.StudentInfo;
import com.cot.ummu.entity.concretes.user.User;
import com.cot.ummu.entity.enums.Note;
import com.cot.ummu.entity.enums.RoleType;
import com.cot.ummu.payload.mappers.StudentInfoMapper;
import com.cot.ummu.payload.messages.SuccessMessages;
import com.cot.ummu.payload.request.bussines.StudentInfoRequest;
import com.cot.ummu.payload.response.businnes.ResponseMessage;
import com.cot.ummu.payload.response.businnes.StudentInfoResponse;
import com.cot.ummu.repository.businnes.StudentInfoRepository;
import com.cot.ummu.service.helpar.MethodHelper;
import com.cot.ummu.service.helpar.StudentInfoHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class StudentInfoService {

  private final StudentInfoRepository studentInfoRepository;
  private final MethodHelper methodHelper;
  private final LessonService lessonService;
  private final EducationTermService educationTermService;
  private final StudentInfoHelper studentInfoHelper;
  private final StudentInfoMapper studentInfoMapper;

  public ResponseMessage<StudentInfoResponse> saveStudentInfo(HttpServletRequest httpServletRequest,
                                                              StudentInfoRequest studentInfoRequest) {
    String teacherUserName = (String) httpServletRequest.getAttribute("username");
    User teacher = methodHelper.loadByUsername(teacherUserName);
    //validate student id
    User student = methodHelper.isUserExist(studentInfoRequest.getStudentId());
    //validate user is really a student
    methodHelper.checkUserRole(student, RoleType.STUDENT);
    //validate and fetch lesson
    Lesson lesson = lessonService.isLessonExistById(studentInfoRequest.getLessonId());
    //validate and fetch education term
    EducationTerm educationTerm = educationTermService.isEducationTermExist(studentInfoRequest.getEducationTermId());
    //student should have only one student info for a lesson
    studentInfoHelper.validateLessonDuplication(studentInfoRequest.getStudentId(),
        lesson.getLessonName());
    Note note = studentInfoHelper.checkLetterGrade(studentInfoHelper.calculateAverageScore(
        studentInfoRequest.getMidtermExam(), studentInfoRequest.getFinalExam()));
    //mapping
    StudentInfo studentInfo = studentInfoMapper.mapStudentInfoRequestToStudentInfo(
        studentInfoRequest,
        note,
        studentInfoHelper.calculateAverageScore(studentInfoRequest.getMidtermExam(), studentInfoRequest.getFinalExam()));
    //set missing props.
    studentInfo.setStudent(student);
    studentInfo.setLesson(lesson);
    studentInfo.setEducationTerm(educationTerm);
    studentInfo.setTeacher(teacher);
    StudentInfo savedStudentInfo = studentInfoRepository.save(studentInfo);
    return ResponseMessage.<StudentInfoResponse>builder()
        .message(SuccessMessages.STUDENT_INFO_SAVE)
        .returnBody(studentInfoMapper.mapStudentInfoToStudentInfoResponse(savedStudentInfo))
        .build();
  }
}
