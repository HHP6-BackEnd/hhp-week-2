package hhplus.lecture.domain.service;


import hhplus.lecture.infrastructure.entity.ApplicationSchedule;
import hhplus.lecture.infrastructure.entity.Lecture;

import java.util.List;

public interface ApplicationScheduleService {
    ApplicationSchedule getLectureApplicationSchedule(Lecture lecture);

    List<Lecture> getAvailableLectures(List<Long> appliedLectureIds);
}
