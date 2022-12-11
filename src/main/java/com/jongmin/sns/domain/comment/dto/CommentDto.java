package com.jongmin.sns.domain.comment.dto;

import com.jongmin.sns.domain.comment.entity.Comment;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentDto {

    private final Long id;
    private final String comment;
    private final Long userId;
    private final String userName;
    private final Long postId;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final LocalDateTime deletedAt;

    public static CommentDto from(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .comment(comment.getComment())
                .userId(comment.getUser().getId())
                .userName(comment.getUser().getUserName())
                .postId(comment.getPost().getId())
                .createdAt(comment.getCreatedAt())
                .modifiedAt(comment.getModifiedAt())
                .deletedAt(comment.getDeletedAt())
                .build();
    }

}
