package com.jongmin.sns.domain.post.dto.request;

import com.jongmin.sns.domain.post.dto.PostDto;
import com.jongmin.sns.domain.user.dto.UserDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PostCreateRequest {

    private String title;
    private String body;

    public static PostCreateRequest of(String title, String content) {
        return new PostCreateRequest(title, content);
    }

    public PostDto toDto(UserDto dto) {
        return PostDto.of(title, body, dto);
    }
}
