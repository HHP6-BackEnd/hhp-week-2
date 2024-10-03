package hhplus.lecture.interfaces.controller;

import hhplus.lecture.application.command.ApplicationHistoriesCommand;
import hhplus.lecture.application.command.ApplyLectureCommand;
import hhplus.lecture.application.command.AvailableLecturesCommand;
import hhplus.lecture.application.facade.LectureFacade;
import hhplus.lecture.application.info.ApplicationHistoriesInfo;
import hhplus.lecture.application.info.ApplyLectureInfo;
import hhplus.lecture.application.info.AvailableLecturesInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/lecture")
@RequiredArgsConstructor
public class LectureController {

    private final LectureFacade lectureFacade;

    // 특강 신청
    @PostMapping("/apply/{userId}/{lectureId}")
    public ResponseEntity<ApplyLectureInfo> applyLecture(
            @PathVariable("userId") Long userId,
            @PathVariable("lectureId") Long lectureId
    ) {
        return ResponseEntity.ok((lectureFacade.applyLecture(new ApplyLectureCommand(userId, lectureId))));
    }

    // 신청 가능 특강 목록
    @GetMapping("/lectures/{userId}")
    public ResponseEntity<List<AvailableLecturesInfo>> getAvailableLectures(
            @PathVariable("userId") Long userId
    ) {
        return ResponseEntity.ok(lectureFacade.getAvailableLectures(new AvailableLecturesCommand(userId, LocalDate.now())));
    }

    // 특강 신청 히스토리 조회
    @GetMapping("/histories/{userId}")
    public ResponseEntity<List<ApplicationHistoriesInfo>> getApplicationHistories(
        @PathVariable(name = "userId") Long userId
    ) {
        return ResponseEntity.ok(lectureFacade.getApplicationHistories(new ApplicationHistoriesCommand(userId)));
    }

}
