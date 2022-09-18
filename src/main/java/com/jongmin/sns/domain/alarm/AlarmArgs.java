package com.jongmin.sns.domain.alarm;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AlarmArgs {

    // 알람을 발생시킨 사람
    private Long fromUserId;
    // 알람의 주체(post)
    private Long targetId;
}