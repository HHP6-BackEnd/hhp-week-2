package hhplus.lecture.infrastructure.repositoryImpl;

import hhplus.lecture.domain.repository.UserRepository;
import hhplus.lecture.infrastructure.entity.User;
import hhplus.lecture.infrastructure.jparepository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SimpleUserRepository implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public User getUser(Long userId) {
        return userJpaRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원을 찾을 수 없습니다."));
    }
}
