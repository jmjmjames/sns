package com.jongmin.sns.repository;

import com.jongmin.sns.domain.Post;
import com.jongmin.sns.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = "select p from Post p join fetch p.user",
            countQuery = "select count(p) from Post p where p.user = :user")
    Page<Post> findAllByUser(@Param("user") User user, Pageable pageable);

}
