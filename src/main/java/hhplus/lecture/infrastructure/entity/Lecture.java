package hhplus.lecture.infrastructure.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "LECTURE")
public class Lecture extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("특강 고유 번호")
    private Long lectureId;

    @Comment("특강 제목")
    private String lectureTitle;

    @Comment("특강 설명")
    private String lectureDescription;

    @Comment("특강 발표자")
    private String lecturePresenter;

    @Builder
    public Lecture(Long lectureId, String lectureTitle, String lectureDescription, String lecturePresenter) {
        this.lectureId = lectureId;
        this.lectureTitle = lectureTitle;
        this.lectureDescription = lectureDescription;
        this.lecturePresenter = lecturePresenter;
    }

}