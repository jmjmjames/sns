package com.jongmin.sns.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jongmin.sns.dto.UserDto;
import com.jongmin.sns.dto.request.user.UserJoinRequest;
import com.jongmin.sns.dto.request.user.UserLoginRequest;
import com.jongmin.sns.exception.ErrorCode;
import com.jongmin.sns.exception.SnsApplicationException;
import com.jongmin.sns.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

        UserJoinRequest joinRequest = UserJoinRequest.of(userName, password);
        when(userService.join(joinRequest.toDto())).thenReturn(mock(UserDto.class));

        // When & Then
        mockMvc.perform(post("/api/v1/users/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(joinRequest))
                ).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void 회원가입시에_이미_userName으로_가입된_회원이_있으면_회원가입_실패_에러반환() throws Exception {
        // Given
        String userName = "name";
        String password = "password";
        UserJoinRequest request = UserJoinRequest.of(userName, password);

        when(userService.join(any())).thenThrow(new SnsApplicationException(
                ErrorCode.DUPLICATED_USER_NAME,
                String.format("%s is duplicated", userName)
        ));

        // When & Then
        mockMvc.perform(post("/api/v1/users/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request))
                ).andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    void 로그인() throws Exception {
        // Given
        String userName = "name";
        String password = "password";
        UserLoginRequest request = UserLoginRequest.of(userName, password);

        when(userService.login(any())).thenReturn("test_token");

        // When & Then
        mockMvc.perform(post("/api/v1/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request))
                ).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void 로그인시_회원가입이_안된_userName을_입력할경우_에러반환() throws Exception {
        // Given
        String userName = "name";
        String password = "password";
        UserLoginRequest request = UserLoginRequest.of(userName, password);

        when(userService.login(any())).thenThrow(new SnsApplicationException(
                ErrorCode.USER_NOT_FOUND,
                String.format("%s not founded", userName)
        ));

        // When & Then
        mockMvc.perform(post("/api/v1/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request))
                ).andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void 로그인시_틀린_패스워드를_입력할경우_에러반환() throws Exception {
        // Given
        String userName = "name";
        String password = "password";
        String wrongPassword = "WRONG!!";
        UserLoginRequest request = UserLoginRequest.of(userName, wrongPassword);

        when(userService.login(any())).thenThrow(new SnsApplicationException(
                ErrorCode.INVALID_PASSWORD
        ));

        // When & Then
        mockMvc.perform(post("/api/v1/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request))
                ).andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void 알람기능() throws Exception {
        when(userService.alarmList(any(), any())).thenReturn(Page.empty());
        mockMvc.perform(get("/api/v1/users/alarm")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    void 알람리스트_요청시_로그인하지_않은_경우() throws Exception {
        when(userService.alarmList(any(), any())).thenReturn(Page.empty());
        mockMvc.perform(get("/api/v1/users/alarm")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

}
