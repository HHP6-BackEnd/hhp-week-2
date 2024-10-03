package hhplus.lecture.application.info;

import hhplus.lecture.infrastructure.entity.ApplicationHistory;

public record ApplyLectureInfo(
        Long userId,
        String userName,
        Long lectureId,
        String lectureTitle,
        String lectureDescription,
        String lecturePresentation
) { public ApplyLectureInfo(ApplicationHistory applicationHistory) {
        this(
            applicationHistory.getUser().getUserId(),
            applicationHistory.getUser().getUserName(),
            applicationHistory.getLecture().getLectureId(),
            applicationHistory.getLecture().getLectureTitle(),
            applicationHistory.getLecture().getLectureDescription(),
            applicationHistory.getLecture().getLecturePresenter()
        );
    }
}
