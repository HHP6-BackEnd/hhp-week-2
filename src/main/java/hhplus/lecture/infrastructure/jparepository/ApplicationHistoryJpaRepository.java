package hhplus.lecture.infrastructure.jparepository;

import hhplus.lecture.infrastructure.entity.ApplicationHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationHistoryJpaRepository extends JpaRepository<ApplicationHistory, Long> {
    List<ApplicationHistory> findAllByUser_UserId(Long userId);

}
