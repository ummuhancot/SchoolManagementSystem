package com.cot.ummu.service.businnes;


import com.cot.ummu.entity.concretes.business.Meet;
import com.cot.ummu.entity.concretes.user.User;
import com.cot.ummu.payload.mappers.MeetingMapper;
import com.cot.ummu.payload.messages.SuccessMessages;
import com.cot.ummu.payload.request.bussines.MeetingRequest;
import com.cot.ummu.payload.response.businnes.MeetingResponse;
import com.cot.ummu.payload.response.businnes.ResponseMessage;
import com.cot.ummu.repository.businnes.MeetingRepository;
import com.cot.ummu.service.helpar.MeetingHelper;
import com.cot.ummu.service.helpar.MethodHelper;
import com.cot.ummu.service.helpar.PageableHelper;
import com.cot.ummu.service.validator.TimeValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MeetingService {

  private final MeetingRepository meetingRepository;
  private final TimeValidator timeValidator;
  private final MeetingMapper meetingMapper;
  private final PageableHelper pageableHelper;
  private final MethodHelper methodHelper;
  private final MeetingHelper meetingHelper;


  public ResponseMessage<MeetingResponse> save(HttpServletRequest httpServletRequest,
                                               MeetingRequest meetingRequest) {
    String username = (String) httpServletRequest.getAttribute("username");
    User teacher = methodHelper.loadByUsername(username);
    methodHelper.checkIsAdvisor(teacher);
    timeValidator.checkStartIsBeforeStop(
        meetingRequest.getStartTime(),
        meetingRequest.getStopTime());
    meetingHelper.checkMeetingConflicts(
        meetingRequest.getStudentIds(),
        teacher.getId(),
        meetingRequest.getDate(),
        meetingRequest.getStartTime(),
        meetingRequest.getStopTime());
    List<User>students = methodHelper.getUserList(meetingRequest.getStudentIds());
    Meet meetToSave = meetingMapper.mapMeetingRequestToMeet(meetingRequest);
    meetToSave.setStudentList(students);
    meetToSave.setAdvisoryTeacher(teacher);
    Meet savedMeeting = meetingRepository.save(meetToSave);
    return ResponseMessage.<MeetingResponse>builder()
        .message(SuccessMessages.MEET_SAVE)
        .returnBody(meetingMapper.mapMeetingToMeetingResponse(savedMeeting))
        .httpStatus(HttpStatus.CREATED)
        .build();
  }

  public ResponseMessage<MeetingResponse> update(@Valid MeetingRequest meetingRequest,
      Long meetingId, HttpServletRequest httpServletRequest) {
    Meet existingMeeting = meetingHelper.isMeetingExistById(meetingId);
    //validate is logged in teacher owner of this meeting
    meetingHelper.isMeetingMatchedWithTeacher(existingMeeting, httpServletRequest);
    timeValidator.checkStartIsBeforeStop(
        meetingRequest.getStartTime(),
        meetingRequest.getStopTime());
    meetingHelper.checkMeetingConflicts(
        meetingRequest.getStudentIds(),
        existingMeeting.getAdvisoryTeacher().getId(),
        meetingRequest.getDate(),
        meetingRequest.getStartTime(),
        meetingRequest.getStopTime());
    List<User>students = methodHelper.getUserList(meetingRequest.getStudentIds());
    Meet meetingToUpdate = meetingMapper.mapMeetingRequestToMeet(meetingRequest);
    meetingToUpdate.setStudentList(students);
    meetingToUpdate.setAdvisoryTeacher(existingMeeting.getAdvisoryTeacher());
    meetingToUpdate.setId(existingMeeting.getId());
    Meet updatedMeeting = meetingRepository.save(meetingToUpdate);
    return ResponseMessage.<MeetingResponse>builder()
        .message(SuccessMessages.MEET_UPDATE)
        .returnBody(meetingMapper.mapMeetingToMeetingResponse(updatedMeeting))
        .build();
  }

  public ResponseEntity<ResponseMessage<Page<MeetingResponse>>> getAllByPageTeacher(
          int page, int size,
          HttpServletRequest httpServletRequest) {
    String username = (String) httpServletRequest.getAttribute("username");
    User teacher = methodHelper.loadByUsername(username);
    Pageable pageable = pageableHelper.getPageableByPageAndSize(page,size);
    Page<Meet> meetings = meetingRepository.findByAdvisoryTeacher_Id(teacher.getId(), pageable);
    Page<MeetingResponse> meetingResponses = meetings.map(meetingMapper::mapMeetingToMeetingResponse);
    ResponseMessage<Page<MeetingResponse>> responseMessage =
            ResponseMessage.<Page<MeetingResponse>>builder()
                    .message(SuccessMessages.MEET_FOUND)
                    .returnBody(meetingResponses)
                    .httpStatus(HttpStatus.OK)
                    .build();
    return ResponseEntity.status(responseMessage.getHttpStatus()).body(responseMessage);
  }


}
