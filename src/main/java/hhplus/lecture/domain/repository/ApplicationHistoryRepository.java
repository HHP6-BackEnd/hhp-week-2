package hhplus.lecture.domain.repository;


import hhplus.lecture.infrastructure.entity.ApplicationHistory;
import hhplus.lecture.infrastructure.entity.User;

import java.util.List;

public interface ApplicationHistoryRepository {

    List<ApplicationHistory> getUserApplicationHistories(User user);

    ApplicationHistory saveApplicationHistory(ApplicationHistory applicationHistory);
}