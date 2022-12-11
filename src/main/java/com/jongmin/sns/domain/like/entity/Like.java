package com.jongmin.sns.domain.like.entity;

import com.jongmin.sns.domain.post.entity.Post;
import com.jongmin.sns.domain.user.entity.User;
import com.jongmin.sns.global.entity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE likes SET deleted_at = NOW() WHERE id = ?")
@Where(clause = "deleted_at is NULL")
@Table(name = "likes")
@Entity
public class Like extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Builder(access = AccessLevel.PRIVATE)
    private Like(User user, Post post) {
        this.user = user;
        this.post = post;
    }

    public static Like of(User user, Post post) {
        return Like.builder()
                .user(user)
                .post(post)
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Like like = (Like) o;
        return id.equals(like.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
