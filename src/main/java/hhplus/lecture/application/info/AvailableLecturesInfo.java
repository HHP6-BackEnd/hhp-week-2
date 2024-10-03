package hhplus.lecture.application.info;

import hhplus.lecture.infrastructure.entity.Lecture;

public record AvailableLecturesInfo(
        Long lectureId,
        String lectureTitle,
        String lectureDescription,
        String lecturePresentation
) { public AvailableLecturesInfo(Lecture lecture) {
        this(
                lecture.getLectureId(),
                lecture.getLectureTitle(),
                lecture.getLectureDescription(),
                lecture.getLecturePresenter()
        );
    }
}