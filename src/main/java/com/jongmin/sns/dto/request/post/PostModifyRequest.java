package com.jongmin.sns.dto.request.post;

import com.jongmin.sns.dto.PostDto;
import com.jongmin.sns.dto.UserDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PostModifyRequest {
    private String title;
    private String body;

    public static PostModifyRequest of(String title, String content) {
        return new PostModifyRequest(title, content);
    }

    public PostDto toDto(UserDto dto) {
        return PostDto.of(title, body, dto);
    }

}
