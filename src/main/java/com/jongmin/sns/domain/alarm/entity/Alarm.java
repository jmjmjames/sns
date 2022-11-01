package com.jongmin.sns.domain.alarm.entity;

import com.jongmin.sns.domain.user.entity.User;
import com.jongmin.sns.global.entity.BaseTimeEntity;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@SQLDelete(sql = "UPDATE alarm SET deleted_at = NOW() WHERE id = ?")
@Where(clause = "deleted_at is NULL")
@Table(indexes = {
        @Index(name = "user_id_idx", columnList = "user_id")
})
@Entity
public class Alarm extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 알람을 받을 사람
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private AlarmType alarmType;

    @Type(type = "jsonb")
    @Column(columnDefinition = "json")
    private AlarmArgs args;

    public static Alarm of(User user, AlarmType alarmType, AlarmArgs args) {
        Alarm alarm = new Alarm();
        alarm.user = user;
        alarm.alarmType = alarmType;
        alarm.args = args;
        return alarm;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Alarm alarm = (Alarm) o;
        return id.equals(alarm.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
