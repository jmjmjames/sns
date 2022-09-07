package com.jongmin.sns.fixture;

import com.jongmin.sns.domain.user.User;
import com.jongmin.sns.domain.user.UserRole;
import com.jongmin.sns.dto.UserDto;

public class UserFixture {

    public static User get(Long userId, String userName, String password) {
        UserDto dto = UserDto.of(userId, userName, password, UserRole.USER, null, null, null);
        return dto.toEntity();
    }
}
