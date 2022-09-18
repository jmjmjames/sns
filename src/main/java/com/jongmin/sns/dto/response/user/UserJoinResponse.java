package com.jongmin.sns.dto.response.user;

import com.jongmin.sns.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public final class UserJoinResponse {
    private Long id;
    private String name;
    public static UserJoinResponse fromDto(UserDto dto) {
        return new UserJoinResponse(
                dto.getId(),
                dto.getUserName()
        );
    }

}
