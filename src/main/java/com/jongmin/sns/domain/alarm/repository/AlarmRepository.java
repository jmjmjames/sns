package com.jongmin.sns.domain.alarm.repository;

import com.jongmin.sns.domain.alarm.entity.Alarm;
import com.jongmin.sns.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {

    @Query(value = "select a from Alarm a join fetch a.user",
            countQuery = "select count(a) from Alarm a where a.user = :user")
    Page<Alarm> findAllByUser(@Param("user") User user, Pageable pageable);

    Page<Alarm> findAllByUserId(Long userId, Pageable pageable);
}
