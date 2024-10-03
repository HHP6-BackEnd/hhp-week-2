package hhplus.lecture.domain.service.Impl;

import hhplus.lecture.domain.repository.ApplicationScheduleRepository;
import hhplus.lecture.domain.service.ApplicationScheduleService;
import hhplus.lecture.infrastructure.entity.ApplicationSchedule;
import hhplus.lecture.infrastructure.entity.Lecture;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SimpleApplicationScheduleService implements ApplicationScheduleService {

    private final ApplicationScheduleRepository applicationScheduleRepository;

    @Override
    @Transactional
    public ApplicationSchedule getLectureApplicationSchedule(Lecture lecture) {
        ApplicationSchedule applicationSchedule = applicationScheduleRepository.getLectureApplicationSchedule(lecture.getLectureId());

        // 정원 초과 여부 검증
        if (applicationSchedule.getCurrentApplicationCount() >= applicationSchedule.getMaxApplicationCount()) {
            throw new IllegalStateException("강의 신청이 마감되었습니다.");  // 정원 초과
        }

        // 신청 기한 검증 (오늘까지는 가능, 내일부터는 불가)
        if (applicationSchedule.getApplicationDeadLine().isBefore(LocalDate.now())) {
            throw new IllegalStateException("강의 신청 기간이 지났습니다.");
        }

        return applicationSchedule;
    }

    @Override
    @Transactional
    public List<Lecture> getAvailableLectures(List<Long> appliedLectureIds) {
        List<Lecture> availableLectures;

        if (appliedLectureIds.isEmpty()) {
            availableLectures = applicationScheduleRepository.getAllLectures().stream()
                    .map(ApplicationSchedule::getLecture)
                    .toList();
        } else {
            availableLectures = applicationScheduleRepository.getAvailableLectures(appliedLectureIds).stream()
                    .map(ApplicationSchedule::getLecture)
                    .toList();
        }

        if (availableLectures.isEmpty()) {
            throw new IllegalStateException("현재 수강신청 가능한 과목이 없습니다.");
        }
        return availableLectures;
    }
}
