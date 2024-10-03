package hhplus.lecture.infrastructure.jparepository;

import hhplus.lecture.infrastructure.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpaRepository extends JpaRepository<User, Long> {
}
