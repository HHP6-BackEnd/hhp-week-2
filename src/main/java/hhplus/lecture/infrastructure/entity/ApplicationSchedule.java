package hhplus.lecture.infrastructure.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "APPLICATION_SCHEDULE")
public class ApplicationSchedule extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("내역 고유 번호")
    private Long scheduleId;

    @Comment("특강 고유 번호")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id", nullable = false)
    private Lecture lecture;

    @Comment("현재 신청 인원")
    private Long currentApplicationCount = 0L;  // 기본값 0으로 설정

    @Comment("최대 신청 인원")
    private Long maxApplicationCount = 30L;  // 기본값 30으로 설정

    @Comment("특강 마감 일자")
    private LocalDate applicationDeadLine;

    @Builder
    public ApplicationSchedule(Lecture lecture, Long currentApplicationCount, Long maxApplicationCount, LocalDate applicationDeadLine) {
        this.lecture = lecture;
        this.currentApplicationCount = currentApplicationCount != null ? currentApplicationCount : 0L;  // null일 경우 0으로 설정
        this.maxApplicationCount = maxApplicationCount != null ? maxApplicationCount : 30L;  // null일 경우 30으로 설정
        this.applicationDeadLine = applicationDeadLine;
    }

    public void increaseCurrentApplicationCount() {
        currentApplicationCount++;
    }
}
