package hhplus.lecture.infrastructure.repositoryImpl;

import hhplus.lecture.domain.repository.ApplicationScheduleRepository;
import hhplus.lecture.infrastructure.entity.ApplicationSchedule;
import hhplus.lecture.infrastructure.jparepository.ApplicationScheduleJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class SimpleApplicationScheduleRepository implements ApplicationScheduleRepository {

    private final ApplicationScheduleJpaRepository applicationScheduleJpaRepository;

    @Override
    public ApplicationSchedule getLectureApplicationSchedule(Long lectureId) {
        return applicationScheduleJpaRepository.findById(lectureId)
                .orElseThrow(() -> new IllegalStateException("해당 강의의 신청 스케쥴이 없습니다."));
    }

    @Override
    public List<ApplicationSchedule> getAvailableLectures(List<Long> appliedLectureIds) {
        return applicationScheduleJpaRepository.findAllByLecture_LectureIdNotInAndApplicationDeadLineGreaterThanEqual(appliedLectureIds, LocalDate.now());

    }

    @Override
    public List<ApplicationSchedule> getAllLectures() {
        return applicationScheduleJpaRepository.findAllByApplicationDeadLineGreaterThanEqual(LocalDate.now());

    }

}
