package com.jongmin.sns.repository;

import com.jongmin.sns.domain.Like;
import com.jongmin.sns.domain.Post;
import com.jongmin.sns.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    Optional<Like> findByUserAndPost(User user, Post post);

    @Query(value = "select count(l) from Like l where l.post = :post")
    Integer countByPost(@Param("post") Post post);

}
