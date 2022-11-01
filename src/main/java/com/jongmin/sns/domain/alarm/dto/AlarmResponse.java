package com.jongmin.sns.domain.alarm.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class AlarmResponse {
    private Long id;
    private String text;
    private LocalDateTime registeredAt;
    private LocalDateTime updatedAt;
    private LocalDateTime removedAt;

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
