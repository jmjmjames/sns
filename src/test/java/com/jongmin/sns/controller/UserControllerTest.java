package com.jongmin.sns.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jongmin.sns.dto.request.user.UserJoinRequest;
import com.jongmin.sns.dto.request.user.UserLoginRequest;
import com.jongmin.sns.domain.constant.UserRole;
import com.jongmin.sns.dto.UserDto;
import com.jongmin.sns.exception.ErrorCode;
import com.jongmin.sns.exception.SnsApplicationException;
import com.jongmin.sns.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    void 회원가입() throws Exception {
        // Given
        String userName = "name";
        String password = "password";
        UserJoinRequest request = UserJoinRequest.of(userName, password);


        when(userService.join(request.toDto())).thenReturn(
                UserDto.of(null, userName, password, UserRole.USER, null, null, null)
        );

        // When & Then
        mockMvc.perform(post("/api/v1/users/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserJoinRequest(userName, password)))
                ).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void 회원가입시에_이미_userName으로_가입된_회원이_있으면_회원가입_실패_에러반환() throws Exception {
        // Given
        String userName = "name";
        String password = "password";
        UserJoinRequest request = UserJoinRequest.of(userName, password);

        when(userService.join(request.toDto())).thenThrow(new SnsApplicationException(
                ErrorCode.DUPLICATED_USER_NAME,
                String.format("%s is duplicated", userName)
        ));

        // When & Then
        mockMvc.perform(post("/api/v1/users/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserJoinRequest(userName, password)))
                ).andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    void 로그인() throws Exception {
        // Given
        String userName = "name";
        String password = "password";
        UserLoginRequest request = UserLoginRequest.of(userName, password);

        when(userService.login(request.toDto())).thenReturn("test_token");

        // When & Then
        mockMvc.perform(post("/api/v1/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserLoginRequest(userName, password)))
                ).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void 로그인시_회원가입이_안된_userName을_입력할경우_에러반환() throws Exception {
        // Given
        String userName = "name";
        String password = "password";
        UserLoginRequest request = UserLoginRequest.of(userName, password);

        when(userService.login(request.toDto())).thenThrow(new SnsApplicationException(
                ErrorCode.USER_NOT_FOUND,
                String.format("%s not founded", userName)
        ));

        // When & Then
        mockMvc.perform(post("/api/v1/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserLoginRequest(userName, password)))
                ).andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void 로그인시_틀린_password를_입력할경우_에러반환() throws Exception {
        // Given
        String userName = "name";
        String password = "password";
        String wrongPassword = "WRONG!!";
        UserLoginRequest request = UserLoginRequest.of(userName, wrongPassword);

        when(userService.login(request.toDto())).thenThrow(new SnsApplicationException(
                ErrorCode.INVALID_PASSWORD
        ));

        // When & Then
        mockMvc.perform(post("/api/v1/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserLoginRequest(userName, password)))
                ).andDo(print())
                .andExpect(status().isUnauthorized());
    }
}
