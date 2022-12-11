package com.jongmin.sns.domain.alarm.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AlarmArgs {

    private Long fromUserId;  // 알람을 발생시킨 사람
    private Long targetId;  // 알람의 주체(post)
}
