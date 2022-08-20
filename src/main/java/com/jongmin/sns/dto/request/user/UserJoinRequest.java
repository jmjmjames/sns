package com.jongmin.sns.dto.request.user;


import com.jongmin.sns.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class UserJoinRequest {
    private String name;
    private String password;

    public static UserJoinRequest of(String userName, String password) {
        return new UserJoinRequest(userName, password);
    }

    public UserDto toDto() {
        return UserDto.of(name, password);
    }

}
