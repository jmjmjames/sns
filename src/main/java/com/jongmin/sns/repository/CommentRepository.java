package com.jongmin.sns.repository;

import com.jongmin.sns.domain.Comment;
import com.jongmin.sns.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value = "select c from Comment c join fetch c.user",
            countQuery = "select count(c) from Comment c where c.post = :post")
    Page<Comment> findAllByPost(@Param("post") Post post, Pageable pageable);

}
