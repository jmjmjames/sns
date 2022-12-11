package com.jongmin.sns.domain.alarm.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class AlarmResponse {

    private final Long id;
    private final String text;
    private final LocalDateTime registeredAt;
    private final LocalDateTime updatedAt;
    private final LocalDateTime removedAt;

    public static AlarmResponse from(AlarmDto dto) {
        return AlarmResponse.builder()
                .id(dto.getId())
                .text(dto.getAlarmText())
                .registeredAt(dto.getCreatedAt())
                .updatedAt(dto.getModifiedAt())
                .removedAt(dto.getDeletedAt())
                .build();
    }

}
