package com.jongmin.sns.domain.post.dto.response;

import com.jongmin.sns.domain.comment.dto.CommentDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CommentResponse {

    private final Long id;
    private final String comment;
    private final Long userId;
    private final String userName;
    private final Long postId;
    private final LocalDateTime registeredAt;
    private final LocalDateTime updatedAt;
    private final LocalDateTime removedAt;

    public static CommentResponse from(CommentDto dto) {
        return CommentResponse.builder()
                .id(dto.getId())
                .comment(dto.getComment())
                .userId(dto.getUserId())
                .userName(dto.getUserName())
                .postId(dto.getPostId())
                .registeredAt(dto.getCreatedAt())
                .updatedAt(dto.getModifiedAt())
                .removedAt(dto.getDeletedAt())
                .build();
    }
}
