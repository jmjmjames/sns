package com.jongmin.sns.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRole {
    USER("ROLE_USER"), // ROLE_ : Spring Security 권한 표현을 하는 문자열 규칙
    ADMIN("ROLE_ADMIN"),
    ;

    private final String name;

}
