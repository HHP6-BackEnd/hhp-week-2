package hhplus.lecture.infrastructure.jparepository;

import hhplus.lecture.infrastructure.entity.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureJpaRepository extends JpaRepository<Lecture, Long> {
}
