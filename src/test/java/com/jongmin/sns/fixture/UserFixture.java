package com.jongmin.sns.fixture;

import com.jongmin.sns.domain.User;
import com.jongmin.sns.domain.constant.UserRole;
import com.jongmin.sns.dto.UserDto;

public class UserFixture {

    public static User get(Long userId, String userName, String password) {
        UserDto dto = UserDto.of(userId, userName, password, UserRole.USER, null, null, null);
        return dto.toEntity();
    }
}
