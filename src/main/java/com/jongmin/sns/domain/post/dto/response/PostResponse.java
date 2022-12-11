package com.jongmin.sns.domain.post.dto.response;

import com.jongmin.sns.domain.post.dto.PostDto;
import com.jongmin.sns.domain.user.dto.response.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PostResponse {

    private final Long id;
    private final String title;
    private final String body;
    private final UserResponse user;
    private final LocalDateTime registeredAt;
    private final LocalDateTime updatedAt;

    public static PostResponse from(PostDto dto) {
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
