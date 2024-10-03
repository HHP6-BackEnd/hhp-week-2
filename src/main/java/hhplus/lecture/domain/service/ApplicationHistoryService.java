package hhplus.lecture.domain.service;


import hhplus.lecture.infrastructure.entity.ApplicationHistory;
import hhplus.lecture.infrastructure.entity.Lecture;
import hhplus.lecture.infrastructure.entity.User;

import java.util.List;

public interface ApplicationHistoryService {
    ApplicationHistory saveApplicationHistory(User user, Lecture lecture);
    List<ApplicationHistory> getUserAppliedLectureHistories(User user);
    List<Long> getUserAppliedLectureIds(User user);
}
