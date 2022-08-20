package com.jongmin.sns.controller;

import com.jongmin.sns.dto.UserDto;
import com.jongmin.sns.dto.request.user.UserJoinRequest;
import com.jongmin.sns.dto.request.user.UserLoginRequest;
import com.jongmin.sns.dto.response.Response;
import com.jongmin.sns.dto.response.user.UserJoinResponse;
import com.jongmin.sns.dto.response.user.UserLoginResponse;
import com.jongmin.sns.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public Response<UserJoinResponse> join(@RequestBody UserJoinRequest request) {
        UserDto dto = userService.join(request.toDto());
        return Response.success(UserJoinResponse.fromDto(dto));
    }

    @PostMapping("/login")
    public Response<UserLoginResponse> login(@RequestBody UserLoginRequest request) {
        String token = userService.login(request.toDto());
        return Response.success(new UserLoginResponse(token));
    }
}

