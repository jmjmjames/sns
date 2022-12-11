package com.jongmin.sns.domain.post.dto;

import com.jongmin.sns.domain.post.entity.Post;
import com.jongmin.sns.domain.user.dto.UserDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public final class PostDto {

    private final Long id;
    private final String title;
    private final String content;
    private final UserDto userDto;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final LocalDateTime deletedAt;

    public PostDto(Long id, String title, String content, UserDto userDto, LocalDateTime createdAt, LocalDateTime modifiedAt, LocalDateTime deletedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.userDto = userDto;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.deletedAt = deletedAt;
    }

    public static PostDto of(String title, String content, UserDto userDto) {
        return PostDto.builder()
                .title(title)
                .content(content)
                .userDto(userDto)
                .build();
    }

    public static PostDto of(Long id, String title, String content, UserDto userDto, LocalDateTime createdAt, LocalDateTime modifiedAt, LocalDateTime deletedAt) {
        return PostDto.builder()
                .id(id)
                .title(title)
                .content(content)
                .userDto(userDto)
                .createdAt(createdAt)
                .modifiedAt(modifiedAt)
                .deletedAt(deletedAt)
                .build();
    }

    public static PostDto from(Post post) {
        return PostDto.of(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                UserDto.from(post.getUser()),
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
