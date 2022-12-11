package com.jongmin.sns.domain.user.dto.response;

import com.jongmin.sns.domain.user.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class UserJoinResponse {
    private final Long id;
    private final String name;

    public static UserJoinResponse from(UserDto dto) {
        return new UserJoinResponse(
                dto.getId(),
                dto.getUsername()
        );
    }
}
