package com.jongmin.sns.domain.user.dto.response;

import com.jongmin.sns.domain.user.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public final class UserResponse {

    private final Long id;
    private final String name;

    public static UserResponse fromDto(UserDto dto) {
        return new UserResponse(
                dto.getId(),
                dto.getUsername()
        );
    }

}
