package com.cot.ummu.controller.user;

import com.cot.ummu.payload.request.user.UserRequest;
import com.cot.ummu.payload.response.businnes.ResponseMessage;
import com.cot.ummu.payload.response.user.UserResponse;
import com.cot.ummu.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {


    private final UserService userService;

    //body
    //path
    //header olabilir.

    @PostMapping("/save/{userRole}")
    public ResponseEntity<ResponseMessage<UserResponse>> saveUser(@RequestBody @Valid UserRequest userRequest,
                                                                  @PathVariable String userRole
                                                                  ) {
        return ResponseEntity.ok(userService.saveUser(userRequest,userRole));
    }


}
