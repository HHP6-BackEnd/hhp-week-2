package hhplus.lecture.application.facade;

import hhplus.lecture.application.command.ApplicationHistoriesCommand;
import hhplus.lecture.application.command.ApplyLectureCommand;
import hhplus.lecture.application.command.AvailableLecturesCommand;
import hhplus.lecture.application.info.ApplicationHistoriesInfo;
import hhplus.lecture.application.info.ApplyLectureInfo;
import hhplus.lecture.application.info.AvailableLecturesInfo;
import hhplus.lecture.domain.service.ApplicationHistoryService;
import hhplus.lecture.domain.service.ApplicationScheduleService;
import hhplus.lecture.domain.service.LectureService;
import hhplus.lecture.domain.service.UserService;
import hhplus.lecture.infrastructure.entity.ApplicationHistory;
import hhplus.lecture.infrastructure.entity.ApplicationSchedule;
import hhplus.lecture.infrastructure.entity.Lecture;
import hhplus.lecture.infrastructure.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class LectureFacade {

    private final UserService userService;
    private final LectureService lectureService;
    private final ApplicationHistoryService applicationHistoryService;
    private final ApplicationScheduleService applicationScheduleService;

    @Transactional
    public ApplyLectureInfo applyLecture(ApplyLectureCommand applyLectureCommand) {

        User user = userService.getUser(applyLectureCommand.userId());
        Lecture lecture = lectureService.getLecture(applyLectureCommand.lectureId());
        ApplicationSchedule applicationSchedule = applicationScheduleService.getLectureApplicationSchedule(lecture);
        ApplicationHistory applicationHistory = applicationHistoryService.saveApplicationHistory(user, lecture);
        applicationSchedule.increaseCurrentApplicationCount();
        return new ApplyLectureInfo(applicationHistory);
    }

    @Transactional
    public List<AvailableLecturesInfo> getAvailableLectures(AvailableLecturesCommand availableLecturesCommand) {

        User user = userService.getUser(availableLecturesCommand.userId());
        // 해당 유저의 신청된 강의 목록 조회
        List<Long> userAppliedLectureIds = applicationHistoryService.getUserAppliedLectureIds(user);
        List<Lecture> availableLectures = applicationScheduleService.getAvailableLectures(userAppliedLectureIds);

        return availableLectures.stream()
                .map(AvailableLecturesInfo::new)
                .toList();

    }
    @Transactional
    public List<ApplicationHistoriesInfo> getApplicationHistories(ApplicationHistoriesCommand applicationHistoriesCommand) {

        User user = userService.getUser(applicationHistoriesCommand.userId());
        List<ApplicationHistory> applicationHistories = applicationHistoryService.getUserAppliedLectureHistories(user);

        return applicationHistories.stream()
                .map(ApplicationHistoriesInfo::new)
                .toList();
    }

}
