package com.cot.ummu.controller.user;


import com.cot.ummu.payload.request.authentication.LoginRequest;
import com.cot.ummu.payload.response.authentication.AuthenticationResponse;
import com.cot.ummu.service.user.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody @Valid LoginRequest loginRequest){
        return ResponseEntity.ok (authenticationService.authenticate(loginRequest));
    }

}
