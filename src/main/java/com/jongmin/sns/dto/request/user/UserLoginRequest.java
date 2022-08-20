package com.jongmin.sns.dto.request.user;


import com.jongmin.sns.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class UserLoginRequest {
    private String name;
    private String password;

    public static UserLoginRequest of(String userName, String password) {
        return new UserLoginRequest(userName, password);
    }

    public UserDto toDto() {
        return UserDto.of(name, password);
    }

}
