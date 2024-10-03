package hhplus.lecture.domain.service.Impl;

import hhplus.lecture.domain.repository.ApplicationHistoryRepository;
import hhplus.lecture.domain.service.ApplicationHistoryService;
import hhplus.lecture.infrastructure.entity.ApplicationHistory;
import hhplus.lecture.infrastructure.entity.Lecture;
import hhplus.lecture.infrastructure.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SimpleApplicationHistoryService implements ApplicationHistoryService {

    private final ApplicationHistoryRepository applicationHistoryRepository;

    @Override
    @Transactional
    public ApplicationHistory saveApplicationHistory(User user, Lecture lecture) {
        return applicationHistoryRepository.saveApplicationHistory(
                ApplicationHistory.builder()
                        .user(user)
                        .lecture(lecture)
                        .build()
        );
    }

    @Override
    @Transactional
    public List<ApplicationHistory> getUserAppliedLectureHistories(User user) {
        return applicationHistoryRepository.getUserApplicationHistories(user);
    }

    @Override
    @Transactional
    public List<Long> getUserAppliedLectureIds(User user) {
        return applicationHistoryRepository.getUserApplicationHistories(user).stream()
                        .map(ApplicationHistory::getLecture)
                        .map(Lecture::getLectureId)
                        .toList();
    }

}
