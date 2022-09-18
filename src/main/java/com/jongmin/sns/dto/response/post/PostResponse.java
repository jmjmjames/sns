package com.jongmin.sns.dto.response.post;

import com.jongmin.sns.dto.PostDto;
import com.jongmin.sns.dto.response.user.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public final class PostResponse {
    private Long id;
    private String title;
    private String body;
    private UserResponse user;
    private LocalDateTime registeredAt;
    private LocalDateTime updatedAt;

    public static PostResponse fromDto(PostDto dto) {
        return new PostResponse(
                dto.getId(),
                dto.getTitle(),
                dto.getContent(),
                UserResponse.fromDto(dto.getUserDto()),
                dto.getCreatedAt(),
                dto.getModifiedAt()
        );
    }

}
