package com.jongmin.sns.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public final class UserLoginResponse {
    private final String token;
}
