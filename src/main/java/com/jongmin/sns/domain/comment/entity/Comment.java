package com.jongmin.sns.domain.comment.entity;

import com.jongmin.sns.domain.post.entity.Post;
import com.jongmin.sns.domain.user.entity.User;
import com.jongmin.sns.global.entity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE comment SET deleted_at = NOW() WHERE id = ?")
@Where(clause = "deleted_at is NULL")
@Table(name = "comment", indexes = {
        @Index(name = "post_id_idx", columnList = "post_id")
})
@Entity
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    private String comment;

    public static Comment of(User user, Post post, String comment) {
        Comment entity = new Comment();
        entity.user = user;
        entity.post = post;
        entity.comment = comment;
        return entity;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return id.equals(comment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
