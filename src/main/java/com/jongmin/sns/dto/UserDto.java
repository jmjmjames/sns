package com.jongmin.sns.dto;

import com.jongmin.sns.domain.user.User;
import com.jongmin.sns.domain.user.UserRole;
import com.jongmin.sns.dto.security.UserPrincipal;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public final class UserDto {
    private Long id;
    private String userName;
    private String password;
    private UserRole role;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private LocalDateTime deletedAt;

    public static UserDto of(String userName, String password) {
        return new UserDto(null, userName, password, null, null, null, null);
    }

    public static UserDto of(String userName, String password, UserRole role) {
        return new UserDto(null, userName, password, role, null, null, null);
    }

    public static UserDto of(Long id, String userName, String password, UserRole userRole, LocalDateTime createdAt, LocalDateTime modifiedAt, LocalDateTime deletedAt) {
        return new UserDto(id, userName, password, userRole, createdAt, modifiedAt, deletedAt);
    }

    public static UserDto fromEntity(User user) {
        return UserDto.of(
                user.getId(),
                user.getUserName(),
                user.getPassword(),
                user.getRole(),
                user.getCreatedAt(),
                user.getModifiedAt(),
                user.getDeletedAt()
        );
    }

    public User toEntity() {
        return User.of(
                userName,
                password
        );
    }

    public UserPrincipal toPrincipal() {
        return UserPrincipal.of(
                userName,
                password,
                role,
                deletedAt
        );
    }

}
