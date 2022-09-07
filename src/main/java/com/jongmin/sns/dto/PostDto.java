package com.jongmin.sns.dto;

import com.jongmin.sns.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public final class PostDto {
    private final Long id;
    private final String title;
    private final String content;
    private final UserDto userDto;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final LocalDateTime deletedAt;

    public static PostDto of(String title, String content, UserDto userDto) {
        return new PostDto(null, title, content, userDto, null, null, null);
    }

    public static PostDto of(Long id, String title, String content, UserDto userDto, LocalDateTime createdAt, LocalDateTime modifiedAt, LocalDateTime deletedAt) {
        return new PostDto(id, title, content, userDto, createdAt, modifiedAt, deletedAt);
    }

    public static PostDto fromEntity(Post post) {
        return PostDto.of(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                UserDto.fromEntity(post.getUser()),
                post.getCreatedAt(),
                post.getModifiedAt(),
                post.getDeletedAt()
        );
    }

    public Post toEntity() {
        return Post.of(
                title,
                content,
                userDto.toEntity()
        );
    }

}
