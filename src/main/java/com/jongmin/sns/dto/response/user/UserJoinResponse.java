package com.jongmin.sns.dto.response.user;

import com.jongmin.sns.domain.constant.UserRole;
import com.jongmin.sns.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public final class UserJoinResponse {
    private final Long id;
    private final String name;
    private final UserRole role;
    public static UserJoinResponse fromDto(UserDto dto) {
        return new UserJoinResponse(
                dto.getId(),
                dto.getUserName(),
                dto.getRole()
        );
    }

}
