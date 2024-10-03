package hhplus.lecture.domain.service.Impl;

import hhplus.lecture.domain.repository.LectureRepository;
import hhplus.lecture.domain.service.LectureService;
import hhplus.lecture.infrastructure.entity.Lecture;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SimpleLectureService implements LectureService {

    private final LectureRepository lectureRepository;

    @Override
    @Transactional
    public Lecture getLecture(Long lectureId) {
        if (0 >= lectureId) {
            throw new IllegalArgumentException("lectureId 는 0보다 커야합니다.");
        }
        return lectureRepository.getLectureById(lectureId);
    }
}
