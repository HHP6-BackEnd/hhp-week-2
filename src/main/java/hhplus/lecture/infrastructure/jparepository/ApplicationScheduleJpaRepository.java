package hhplus.lecture.infrastructure.jparepository;

import hhplus.lecture.infrastructure.entity.ApplicationSchedule;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationScheduleJpaRepository extends JpaRepository<ApplicationSchedule, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<ApplicationSchedule> findByLecture_LectureId(Long lectureId);

    List<ApplicationSchedule> findAllByLecture_LectureIdNotInAndApplicationDeadLineGreaterThanEqual(List<Long> lectureIds, LocalDate today);

    List<ApplicationSchedule> findAllByApplicationDeadLineGreaterThanEqual(LocalDate today);
}
