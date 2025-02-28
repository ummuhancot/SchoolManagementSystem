package com.cot.ummu.controller.user;


import com.cot.ummu.payload.request.bussines.AddLessonProgram;
import com.cot.ummu.payload.request.user.TeacherRequest;
import com.cot.ummu.payload.response.businnes.ResponseMessage;
import com.cot.ummu.payload.response.user.StudentResponse;
import com.cot.ummu.payload.response.user.UserResponse;
import com.cot.ummu.service.user.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/teacher")
@RequiredArgsConstructor
public class TeacherController {

  private final TeacherService teacherService;

  @PreAuthorize("hasAnyAuthority('Admin')")
  @PostMapping("/save")
  public ResponseMessage<UserResponse> saveTeacher(
      @RequestBody @Valid TeacherRequest teacherRequest) {
    return teacherService.saveTeacher(teacherRequest);
  }


  @PreAuthorize("hasAnyAuthority('Admin')")
  @PutMapping("/update/{userId}")
  public ResponseMessage<UserResponse>updateTeacher(
      @RequestBody @Valid TeacherRequest teacherRequest,
      @PathVariable long userId){
    return teacherService.updateTeacherById(teacherRequest,userId);
  }


  //teacher will log-in then get all students who are assigned to him/her via lesson programs
  @PreAuthorize("hasAnyAuthority('Teacher')")
  @GetMapping("/getByAdvisorTeacher")
  public List<StudentResponse>getAllStudentByAdvisorTeacher(
      HttpServletRequest httpServletRequest){
    return teacherService.getAllStudentByAdvisorTeacher(httpServletRequest);
  }

  //managers can add lesson programs to teacher
  @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean')")
  @PostMapping("/addLessonProgram")
  public ResponseMessage<UserResponse>addLessonProgram(
      @RequestBody @Valid AddLessonProgram lessonProgram){
    return teacherService.addLessonProgram(lessonProgram);
  }


  @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean')")
  @DeleteMapping("/deleteTeacherById/{teacherId}")
  public ResponseMessage<UserResponse> deleteTeacherById(@PathVariable Long teacherId){
    return teacherService.deleteTeacherById(teacherId);
  }

  //TODO BELKIS -> not all users
  //getAllByTeacher

  //TODO KERIM
  //getAllTeacherByPage
  @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean')")
  @GetMapping("/getAllTeacherByPage")
  public ResponseEntity<Page<UserResponse>> getAllTeacherByPage(
          @RequestParam(value = "page", defaultValue = "0") int page,
          @RequestParam(value = "size", defaultValue = "10") int size,
          @RequestParam(value = "sort", defaultValue = "name") String sort,
          @RequestParam(value = "type", defaultValue = "desc") String type) {

    Page<UserResponse> teacherResponses = teacherService.getAllTeacherByPage(page, size, sort, type);

    return ResponseEntity.ok(teacherResponses);
  }




}
