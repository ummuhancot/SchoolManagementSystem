package com.cot.ummu.controller.businnes;


import com.cot.ummu.payload.request.bussines.MeetingRequest;
import com.cot.ummu.payload.response.businnes.MeetingResponse;
import com.cot.ummu.payload.response.businnes.ResponseMessage;
import com.cot.ummu.service.businnes.MeetingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/meeting")
@RestController
public class MeetingController {

  public final MeetingService meetingService;


  //TODO bu zamana kadar return olarak verilen response message icindeki
  //response status calismiyor. Bunu asagidaki annotation ile giderebilirirz.
  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize("hasAnyAuthority('Teacher')")
  @PostMapping("/save")
  public ResponseMessage<MeetingResponse> saveMeeting(
      HttpServletRequest httpServletRequest,
      @RequestBody @Valid MeetingRequest meetingRequest){
    return meetingService.save(httpServletRequest,meetingRequest);
  }

  @PreAuthorize("hasAnyAuthority('Teacher')")
  @PutMapping("/update/{meetingId}")
  public ResponseMessage<MeetingResponse>updateMeeting(
      @RequestBody @Valid MeetingRequest meetingRequest,
      @PathVariable Long meetingId,
      HttpServletRequest httpServletRequest){
    return meetingService.update(meetingRequest,meetingId,httpServletRequest);
  }


  //TODO furkan
  @PreAuthorize("hasAnyAuthority('Admin','Teacher')")
  @DeleteMapping("/delete/{meetingId}")
  public ResponseMessage deleteById(@PathVariable Long meetingId){
    //return meetingService.deleteById(meetingId);
    return null;
  }

  @PreAuthorize("hasAnyAuthority('Teacher')")
  @GetMapping("/getAllByPageTeacher")
  public ResponseEntity<ResponseMessage<Page<MeetingResponse>>> getAllByPageTeacher(
          HttpServletRequest httpServletRequest,
          @RequestParam(value = "page",defaultValue = "0") int page,
          @RequestParam(value = "size",defaultValue = "10") int size){
    return meetingService.getAllByPageTeacher(page,size,httpServletRequest);
  }

  //TODO edip
  @PreAuthorize("hasAnyAuthority('Teacher','Student')")
  @GetMapping("/getAll")
  public List<MeetingResponse> getAllMeetings(HttpServletRequest httpServletRequest){
    //return meetingService.getAll(httpServletRequest);
    return null;
  }

  //TODO ismail
  @PreAuthorize("hasAnyAuthority('Admin')")
  @GetMapping("/getAllByPage")
  public Page<MeetingResponse>getAllByPage(
      @RequestParam(value = "page") int page,
      @RequestParam(value = "size") int size){
    //return meetingService.getAllByPage(page, size);
    return null;
  }





}
