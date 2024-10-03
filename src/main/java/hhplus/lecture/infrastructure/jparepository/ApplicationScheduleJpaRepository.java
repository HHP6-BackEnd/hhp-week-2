package hhplus.lecture.infrastructure.jparepository;

import hhplus.lecture.infrastructure.entity.ApplicationSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ApplicationScheduleJpaRepository extends JpaRepository<ApplicationSchedule, Long> {
    List<ApplicationSchedule> findAllByLecture_LectureIdNotInAndApplicationDeadLineGreaterThanEqual(List<Long> lectureIds, LocalDate today);
    List<ApplicationSchedule> findAllByApplicationDeadLineGreaterThanEqual(LocalDate today);
}
