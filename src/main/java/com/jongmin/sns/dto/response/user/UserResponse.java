package com.jongmin.sns.dto.response.user;

import com.jongmin.sns.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public final class UserResponse {
    private Long id;
    private String name;

    public static UserResponse fromDto(UserDto dto) {
        return new UserResponse(
                dto.getId(),
                dto.getUserName()
        );
    }

}
