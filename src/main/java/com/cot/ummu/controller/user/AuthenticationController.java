package com.cot.ummu.controller.user;


import com.cot.ummu.payload.messages.SuccessMessages;
import com.cot.ummu.payload.request.authentication.LoginRequest;
import com.cot.ummu.payload.request.authentication.UpdatePasswordRequest;
import com.cot.ummu.payload.response.authentication.AuthenticationResponse;
import com.cot.ummu.service.user.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService authenticationService;


  @PostMapping("/login")
  public ResponseEntity<AuthenticationResponse>authenticate(
      @RequestBody @Valid LoginRequest loginRequest){
    return ResponseEntity.ok(authenticationService.authenticate(loginRequest));
  }


  @Deprecated(since = "2025-10-10",forRemoval = true )
  @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean','Teacher','Student')")
  @PostMapping("/changePassword")
  public ResponseEntity<String>updatePassword(
          @RequestBody @Valid UpdatePasswordRequest updatePasswordRequest,
          HttpServletRequest httpServletRequest){
    authenticationService.changePassword(updatePasswordRequest,httpServletRequest);
    return ResponseEntity.ok(SuccessMessages.PASSWORD_CHANGED_RESPONSE_MESSAGE);
  }

}
