package com.cot.ummu.controller.businnes;


import com.cot.ummu.entity.concretes.business.Lesson;
import com.cot.ummu.payload.request.bussines.LessonRequest;
import com.cot.ummu.payload.response.businnes.LessonResponse;
import com.cot.ummu.payload.response.businnes.ResponseMessage;
import com.cot.ummu.service.businnes.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/lesson")
@RequiredArgsConstructor
public class LessonController {

  private final LessonService lessonService;

  @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean')")
  @PostMapping("/save")
  public ResponseMessage<LessonResponse> saveLesson(
      @RequestBody @Valid LessonRequest lessonRequest) {
    return lessonService.saveLesson(lessonRequest);
  }

  @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean')")
  @PutMapping("/update/{lessonId}")
  public ResponseEntity<LessonResponse>updateLessonById(
      @PathVariable Long lessonId,
      @RequestBody @Valid LessonRequest lessonRequest){
    return ResponseEntity.ok(lessonService.updateLesson(lessonRequest,lessonId));
  }


  @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean')")
  @DeleteMapping("/delete/{lessonId}")
  public ResponseMessage deleteLesson(@PathVariable Long lessonId) {
    return lessonService.deleteLesson(lessonId);
  }



  @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean')")
  @GetMapping("/getLessonByName/{lessonName}")
  public ResponseMessage<LessonResponse>getLessonByName(
          @PathVariable String lessonName){
    return lessonService.findLessonByName(lessonName);

  }


  @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean')")
  @GetMapping("/getLessonByPage")
  public Page<LessonResponse> findLessonByPage(
      @RequestParam(value = "page",defaultValue = "0") int page,
      @RequestParam(value = "size",defaultValue = "10") int size,
      @RequestParam(value = "sort",defaultValue = "lessonName") String sort,
      @RequestParam(value = "type",defaultValue = "desc") String type
  ){
    return lessonService.getLessonByPage(page,size,sort,type);
  }


  @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean')")
  @GetMapping("/getAllByIdSet")
  public List<Lesson> getAllByIdSet(@RequestParam(name = "lessonId") List<Long> idSet){
    return lessonService.getAllByIdSet(idSet);
  }





}
//