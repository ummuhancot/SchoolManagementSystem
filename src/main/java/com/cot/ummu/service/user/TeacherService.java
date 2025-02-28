package com.cot.ummu.service.user;

import com.cot.ummu.entity.concretes.business.LessonProgram;
import com.cot.ummu.entity.concretes.user.User;
import com.cot.ummu.entity.enums.RoleType;
import com.cot.ummu.payload.mappers.UserMapper;
import com.cot.ummu.payload.messages.SuccessMessages;
import com.cot.ummu.payload.request.bussines.AddLessonProgram;
import com.cot.ummu.payload.request.user.TeacherRequest;
import com.cot.ummu.payload.response.businnes.ResponseMessage;
import com.cot.ummu.payload.response.user.StudentResponse;
import com.cot.ummu.payload.response.user.UserResponse;
import com.cot.ummu.repository.user.UserRepository;
import com.cot.ummu.service.businnes.LessonProgramService;
import com.cot.ummu.service.helpar.LessonProgramDuplicationHelper;
import com.cot.ummu.service.helpar.MethodHelper;
import com.cot.ummu.service.helpar.PageableHelper;
import com.cot.ummu.service.validator.UniquePropertyValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeacherService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final MethodHelper methodHelper;
  private final UniquePropertyValidator uniquePropertyValidator;
  private final LessonProgramService lessonProgramService;
  private final LessonProgramDuplicationHelper lessonProgramDuplicationHelper;
  private final PageableHelper pageableHelper;

 public ResponseMessage<UserResponse> saveTeacher(TeacherRequest teacherRequest) {
    List<LessonProgram>lessonProgramList =
        lessonProgramService.getLessonProgramById(teacherRequest.getLessonProgramList());
    //validate unique property
    uniquePropertyValidator.checkDuplication(
        teacherRequest.getUsername(),
        teacherRequest.getSsn(),
        teacherRequest.getPhoneNumber(),
        teacherRequest.getEmail());
    User teacher = userMapper.mapUserRequestToUser(teacherRequest, RoleType.TEACHER.getName());
    //set additional props.
    teacher.setIsAdvisor(teacherRequest.getIsAdvisoryTeacher());
    teacher.setLessonProgramList(lessonProgramList);
    User savedTeacher = userRepository.save(teacher);
    return ResponseMessage.<UserResponse>
      builder()
        .message(SuccessMessages.TEACHER_SAVE)
        .httpStatus(HttpStatus.CREATED)
        .returnBody(userMapper.mapUserToUserResponse(savedTeacher))
        .build();
  }

  public ResponseMessage<UserResponse> updateTeacherById(@Valid TeacherRequest teacherRequest,
      long userId) {
    //validate if teacher exist
    User teacher = methodHelper.isUserExist(userId);
    //validate if user is a teacher
    methodHelper.checkUserRole(teacher, RoleType.TEACHER);
    //validate unique props.
    uniquePropertyValidator.checkUniqueProperty(teacher,teacherRequest);
    List<LessonProgram>lessonPrograms = lessonProgramService.getLessonProgramById(teacherRequest.getLessonProgramList());
    User theacherToUpdate = userMapper.mapUserRequestToUser(teacherRequest, RoleType.TEACHER.getName());
    //map missing props.
    theacherToUpdate.setId(userId);
    theacherToUpdate.setLessonProgramList(lessonPrograms);
    theacherToUpdate.setIsAdvisor(teacherRequest.getIsAdvisoryTeacher());
    User savedTeacher = userRepository.save(theacherToUpdate);
    return ResponseMessage.<UserResponse>builder()
        .message(SuccessMessages.TEACHER_UPDATE)
        .returnBody(userMapper.mapUserToUserResponse(savedTeacher))
        .httpStatus(HttpStatus.OK)
        .build();
  }

  public List<StudentResponse> getAllStudentByAdvisorTeacher(
      HttpServletRequest httpServletRequest) {
    String username = (String) httpServletRequest.getAttribute("username");
    User teacher = methodHelper.loadByUsername(username);
    methodHelper.checkIsAdvisor(teacher);
    return userRepository.findByAdvisorTeacherId(teacher.getId())
        .stream()
        .map(userMapper::mapUserToStudentResponse)
        .collect(Collectors.toList());
  }

  public ResponseMessage<UserResponse> addLessonProgram(@Valid AddLessonProgram lessonProgram) {
    User teacher = methodHelper.isUserExist(lessonProgram.getTeacherId());
    methodHelper.checkUserRole(teacher, RoleType.TEACHER);
    List<LessonProgram>lessonPrograms = lessonProgramService.getLessonProgramById(lessonProgram.getLessonProgramId());
    // 1,2,3 -> 3,4,5
    // 1,2,3,3,4,5
    // TODO prevent duplication of lesson programs here
    // KERIM -> move your solution to LessonProgramDuplicationHelper class and call it from here.

    List<LessonProgram> existingLessonPrograms = teacher.getLessonProgramList();
    List<LessonProgram> newLessonPrograms = lessonProgramDuplicationHelper.removeDuplicates(existingLessonPrograms,lessonPrograms);


    teacher.getLessonProgramList().addAll(newLessonPrograms);
    //update with new lesson program list
    User savedTeacher = userRepository.save(teacher);
    return ResponseMessage.<UserResponse>builder()
        .message(SuccessMessages.LESSON_PROGRAM_ADD_TO_TEACHER)
        .returnBody(userMapper.mapUserToUserResponse(savedTeacher))
        .build();
  }

  @Transactional
  public ResponseMessage<UserResponse> deleteTeacherById(Long teacherId) {
    User teacher = methodHelper.isUserExist(teacherId);
    methodHelper.checkUserRole(teacher,RoleType.TEACHER);

    userRepository.removeAdvisorFromStudents(teacherId);
    userRepository.delete(teacher);

    return ResponseMessage.<UserResponse>builder()
            .message(SuccessMessages.ADVISOR_TEACHER_DELETE)
            .httpStatus(HttpStatus.OK)
            .build();
  }

  //second solution but this solution hits DB more than the first solution
  //also fetch data from DB to service layer
  @Transactional
  public String deleteTeacherById2(
      Long teacherId) {
    User teacher = methodHelper.isUserExist(teacherId);
    methodHelper.checkUserRole(teacher, RoleType.TEACHER);
    List<User> students = userRepository.findByAdvisorTeacherId(teacherId);
    if (!students.isEmpty()) {
      students.forEach(student->student.setAdvisorTeacherId(null));
      userRepository.saveAll(students);
    }
    userRepository.delete(teacher);
    return SuccessMessages.ADVISOR_TEACHER_DELETE;
  }


  public Page<UserResponse> getAllTeacherByPage(int page, int size, String sort, String type) {
    Pageable pageable = pageableHelper.getPageable(page, size, sort, type);
    Page<User> teachers = userRepository.findAllByUserRole(RoleType.TEACHER, pageable);

    return teachers.map(userMapper::mapUserToUserResponse);
  }
}
