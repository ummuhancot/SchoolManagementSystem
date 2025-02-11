package com.cot.ummu.controller.user;

import com.cot.ummu.payload.request.user.UserRequest;
import com.cot.ummu.payload.response.abstracts.BaseUserResponse;
import com.cot.ummu.payload.response.businnes.ResponseMessage;
import com.cot.ummu.payload.response.user.UserResponse;
import com.cot.ummu.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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

    @GetMapping("/getUserByPage/{userRole}")
    public ResponseEntity<Page<UserResponse>>getUserByPage(
            @PathVariable String userRole,
            @RequestParam (value = "page",defaultValue = "0") int page,
            @RequestParam (value = "size",defaultValue = "10") int size,
            @RequestParam (value = "sort",defaultValue = "name") String sort,
            @RequestParam (value = "type",defaultValue = "desc") String type){
        Page<UserResponse>userResponses= userService.getUserByPage(page,size,sort,type,userRole);
        return ResponseEntity.ok(userResponses);
    }

    @GetMapping("/getUserById/{userId}")
    public ResponseMessage<BaseUserResponse> getUserById(@PathVariable Long userId){
        return userService.findUserById(userId);

    }

    //responseEntity sevmedim serviceden sildik buraya ekledik
    //http://localhost:8090/user/deleteUserById/1
    @DeleteMapping("/deleteUserById/{userId}")
    public ResponseEntity<String > deleteUserById(@PathVariable Long userId){
        return ResponseEntity.ok(userService.deleteUserById(userId));
    }

}
