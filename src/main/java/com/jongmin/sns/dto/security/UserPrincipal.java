package com.jongmin.sns.dto.security;

import com.jongmin.sns.domain.user.UserRole;
import com.jongmin.sns.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Getter
@AllArgsConstructor
public class UserPrincipal implements UserDetails {
    private String userName;
    private String password;
    private UserRole role;
    private LocalDateTime deletedAt;

    public static UserPrincipal of(String username, String password, UserRole role, LocalDateTime deletedAt) {
        return new UserPrincipal(username, password, role, deletedAt);
    }

    public static UserPrincipal fromDto(UserDto dto) {
        return UserPrincipal.of(dto.getUserName(), dto.getPassword(), dto.getRole(), dto.getDeletedAt());
    }

    public UserDto toDto() {
        return UserDto.of(
                userName,
                password,
                role
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return deletedAt == null;
    }

    @Override
    public boolean isAccountNonLocked() {
        return deletedAt == null;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return deletedAt == null;
    }

    @Override
    public boolean isEnabled() {
        return deletedAt == null;
    }

}
