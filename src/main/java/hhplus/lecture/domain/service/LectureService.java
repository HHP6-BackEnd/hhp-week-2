package hhplus.lecture.domain.service;

import hhplus.lecture.infrastructure.entity.Lecture;

public interface LectureService {
    Lecture getLecture(Long lectureId);
}
