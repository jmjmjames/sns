package com.jongmin.sns.dto;

import com.jongmin.sns.domain.alarm.Alarm;
import com.jongmin.sns.domain.alarm.AlarmArgs;
import com.jongmin.sns.domain.alarm.AlarmType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class AlarmDto {
    private Long id;
    private UserDto userDto;
    private AlarmType alarmType;
    private AlarmArgs args;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private LocalDateTime deletedAt;

    public String getAlarmText() {
        return alarmType.getAlarmText();
    }

    public static AlarmDto fromEntity(Alarm alarm) {
        return AlarmDto.builder()
                .id(alarm.getId())
                .userDto(UserDto.fromEntity(alarm.getUser()))
                .alarmType(alarm.getAlarmType())
                .args(alarm.getArgs())
                .createdAt(alarm.getCreatedAt())
                .modifiedAt(alarm.getModifiedAt())
                .deletedAt(alarm.getDeletedAt())
                .build();
    }

}
