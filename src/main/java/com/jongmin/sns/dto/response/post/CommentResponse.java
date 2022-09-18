package com.jongmin.sns.dto.response.post;

import com.jongmin.sns.dto.CommentDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public final class CommentResponse {
    private Long id;
    private String comment;
    private Long userId;
    private String userName;
    private Long postId;
    private LocalDateTime registeredAt;
    private LocalDateTime updatedAt;
    private LocalDateTime removedAt;

    public static CommentResponse fromDto(CommentDto dto) {
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
