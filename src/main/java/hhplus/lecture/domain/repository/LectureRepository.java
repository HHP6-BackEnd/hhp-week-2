package hhplus.lecture.domain.repository;

import hhplus.lecture.infrastructure.entity.Lecture;

public interface LectureRepository {

    Lecture getLectureById(Long lectureId);
}
