package hhplus.lecture.infrastructure.repositoryImpl;

import hhplus.lecture.domain.repository.LectureRepository;
import hhplus.lecture.infrastructure.entity.Lecture;
import hhplus.lecture.infrastructure.jparepository.LectureJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SimpleLectureRepository implements LectureRepository {

    private final LectureJpaRepository lectureJpaRepository;

    @Override
    public Lecture getLectureById(Long lectureId) {
        return lectureJpaRepository.findById(lectureId)
                .orElseThrow(() -> new IllegalArgumentException("해당 강의를 찾을 수 없습니다."));
    }
}
