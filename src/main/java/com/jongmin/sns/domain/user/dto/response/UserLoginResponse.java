package com.jongmin.sns.domain.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class UserLoginResponse {
    private final String token;
}
