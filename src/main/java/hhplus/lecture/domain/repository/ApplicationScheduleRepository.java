package hhplus.lecture.domain.repository;


import hhplus.lecture.infrastructure.entity.ApplicationSchedule;

import java.util.List;

public interface ApplicationScheduleRepository {
    ApplicationSchedule getLectureApplicationSchedule(Long lectureId);
    List<ApplicationSchedule> getAvailableLectures(List<Long> appliedLectureIds);
    List<ApplicationSchedule> getAllLectures();
}
