package com.jongmin.sns.service;

import com.jongmin.sns.domain.user.User;
import com.jongmin.sns.dto.AlarmDto;
import com.jongmin.sns.dto.UserDto;
import com.jongmin.sns.exception.ErrorCode;
import com.jongmin.sns.exception.SnsApplicationException;
import com.jongmin.sns.repository.AlarmRepository;
import com.jongmin.sns.repository.UserRepository;
import com.jongmin.sns.util.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final AlarmRepository alarmRepository;

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.token-expired-time-ms}")
    private Long expiredTimeMs;


    @Transactional(readOnly = true)
    public UserDto loadUserByUserName(String userName) {
        return userRepository.findByUserName(userName)
                .map(UserDto::fromEntity)
                .orElseThrow(() -> new SnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s not founded", userName))
        );
    }

    public UserDto join(UserDto dto) {
        userRepository.findByUserName(dto.getUserName()).ifPresent(it -> {
            throw new SnsApplicationException(ErrorCode.DUPLICATED_USER_NAME, String.format("%s is duplicated", dto.getUserName()));
        });

        User user = userRepository.save(User.of(dto.getUserName(), encoder.encode(dto.getPassword())));

        return UserDto.fromEntity(user);
    }

    @Transactional(readOnly = true)
    public String login(UserDto dto) {
        User user = userRepository.findByUserName(dto.getUserName())
                .orElseThrow(() -> new SnsApplicationException(
                        ErrorCode.USER_NOT_FOUND,
                        String.format("%s not founded", dto.getUserName())
                ));

        if (!encoder.matches(dto.getPassword(), user.getPassword())) {
            throw new SnsApplicationException(
                    ErrorCode.INVALID_PASSWORD
            );
        }

        return JwtTokenUtils.generateToken(dto.getUserName(), secretKey, expiredTimeMs);
    }

    public Page<AlarmDto> alarmList(String userName, Pageable pageable) {
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new SnsApplicationException(
                        ErrorCode.USER_NOT_FOUND,
                        String.format("%s not founded", userName)
                ));
        return alarmRepository.findAllByUser(user, pageable).map(AlarmDto::fromEntity);
    }

}
