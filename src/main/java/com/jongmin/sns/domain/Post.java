package com.jongmin.sns.domain;

import com.jongmin.sns.domain.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE posts SET deleted_at = NOW() WHERE id = ?")
@Where(clause = "deleted_at is NULL")
@Table(name = "posts")
@Entity
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(name = "title")
    private String title;

    @Setter
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private Post(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public static Post of(String title, String content, User user) {
        return new Post(title, content, user);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return id.equals(post.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
