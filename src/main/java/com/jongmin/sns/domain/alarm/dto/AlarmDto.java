package com.jongmin.sns.domain.alarm.dto;

import com.jongmin.sns.domain.alarm.entity.Alarm;
import com.jongmin.sns.domain.alarm.entity.AlarmArgs;
import com.jongmin.sns.domain.alarm.entity.AlarmType;
import com.jongmin.sns.domain.user.dto.UserDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class AlarmDto {

    private final Long id;
    private final UserDto userDto;
    private final AlarmType alarmType;
    private final AlarmArgs args;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final LocalDateTime deletedAt;

    public String getAlarmText() {
        return alarmType.getAlarmText();
    }

    public static AlarmDto from(Alarm alarm) {
        return AlarmDto.builder()
                .id(alarm.getId())
                .userDto(UserDto.from(alarm.getUser()))
                .alarmType(alarm.getAlarmType())
                .args(alarm.getArgs())
                .createdAt(alarm.getCreatedAt())
                .modifiedAt(alarm.getModifiedAt())
                .deletedAt(alarm.getDeletedAt())
                .build();
    }

}
