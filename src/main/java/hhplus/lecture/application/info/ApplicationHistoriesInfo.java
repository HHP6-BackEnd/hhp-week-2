package hhplus.lecture.application.info;

import hhplus.lecture.infrastructure.entity.ApplicationHistory;
import lombok.Builder;

@Builder
public record ApplicationHistoriesInfo(
        Long userId,
        String userName,
        Long lectureId,
        String lectureTitle,
        String lectureDescription,
        String lecturePresentation
) { public ApplicationHistoriesInfo(ApplicationHistory applicationHistory) {
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
