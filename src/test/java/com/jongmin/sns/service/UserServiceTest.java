package com.jongmin.sns.service;

import com.jongmin.sns.domain.user.User;
import com.jongmin.sns.dto.request.user.UserJoinRequest;
import com.jongmin.sns.dto.request.user.UserLoginRequest;
import com.jongmin.sns.exception.ErrorCode;
import com.jongmin.sns.exception.SnsApplicationException;
import com.jongmin.sns.fixture.UserFixture;
import com.jongmin.sns.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

// when(userRepository.save(any())).thenReturn(mock(User.class));
@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PasswordEncoder encoder;


    @Test
    void 회원가입_정상_동작() {
        // Given
        String userName = "name";
        String password = "password";
        UserJoinRequest request = UserJoinRequest.of(userName, password);
        
        // Mocking
        when(userRepository.findByUserName(userName)).thenReturn(Optional.empty());
        when(encoder.encode(password)).thenReturn("encrypt_password");
        when(userRepository.save(any())).thenReturn(UserFixture.get(1L, userName, password));

        // Then
        assertDoesNotThrow(() -> userService.join(request.toDto()));
    }

    @Test
    void 회원가입시에_아이디_중복되면_회원가입_실패_에러반환() {
        // Given
        String userName = "name";
        String password = "password";
        UserJoinRequest request = UserJoinRequest.of(userName, password);

        User fixture = UserFixture.get(1L, userName, password);

        // Mocking
        when(userRepository.findByUserName(userName)).thenReturn(Optional.of(mock(User.class)));
        when(encoder.encode(password)).thenReturn("encrypt_password");
        when(userRepository.save(any())).thenReturn(Optional.of(fixture));

        // Then
        SnsApplicationException e = assertThrows(SnsApplicationException.class, () -> userService.join(request.toDto()));
        assertThat(ErrorCode.DUPLICATED_USER_NAME).isEqualTo(e.getErrorCode());
    }

    @Test
    void 로그인_정상적으로_동작() {
        // Given
        String userName = "name";
        String password = "password";
        UserLoginRequest request = UserLoginRequest.of(userName, password);


        User fixture = UserFixture.get(1L, userName, password);

        // Mocking
        when(userRepository.findByUserName(userName)).thenReturn(Optional.of(fixture));
        when(encoder.matches(password, fixture.getPassword())).thenReturn(true);

        assertDoesNotThrow(() -> userService.login(request.toDto()));
    }

    @Test
    void 로그인시에_입력된_아이디로_회원가입이_안된_경우() {
        // Given
        String userName = "name";
        String password = "password";
        UserLoginRequest request = UserLoginRequest.of(userName, password);

        // Mocking
        when(userRepository.findByUserName(userName)).thenReturn(Optional.empty());

        // Then
        SnsApplicationException e = assertThrows(SnsApplicationException.class, () -> userService.login(request.toDto()));
        assertThat(ErrorCode.USER_NOT_FOUND).isEqualTo(e.getErrorCode());
    }

    @Test
    void 로그인시_패스워드가_틀린_경우() throws Exception {
        // Given
        String userName = "name";
        String password = "password";
        String wrongPassword = "wrong!";
        UserLoginRequest request = UserLoginRequest.of(userName, wrongPassword);

        User fixture = UserFixture.get(1L, userName, password);

        // Mocking
        when(userRepository.findByUserName(userName)).thenReturn(Optional.of(fixture));

        // Then
        SnsApplicationException e = assertThrows(SnsApplicationException.class, () -> userService.login(request.toDto()));
        assertThat(ErrorCode.INVALID_PASSWORD).isEqualTo(e.getErrorCode());
    }

}
