package hhplus.lecture.infrastructure.repositoryImpl;

import hhplus.lecture.domain.repository.ApplicationHistoryRepository;
import hhplus.lecture.infrastructure.entity.ApplicationHistory;
import hhplus.lecture.infrastructure.entity.User;
import hhplus.lecture.infrastructure.jparepository.ApplicationHistoryJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SimpleApplicationHistoryRepository implements ApplicationHistoryRepository {

    private final ApplicationHistoryJpaRepository applicationHistoryJpaRepository;

    @Override
    public ApplicationHistory saveApplicationHistory(ApplicationHistory applicationHistory) {
        return applicationHistoryJpaRepository.save(applicationHistory);
    }


    @Override
    public List<ApplicationHistory> getUserApplicationHistories(User user) {
        return applicationHistoryJpaRepository.findAllByUser_UserId(user.getUserId());
    }
}