package hhplus.lecture.domain.service.Impl;

import hhplus.lecture.domain.repository.UserRepository;
import hhplus.lecture.domain.service.UserService;
import hhplus.lecture.infrastructure.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SimpleUserService implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public User getUser(Long userId) {
        if (0 >= userId) {
            throw new IllegalArgumentException("userId 는 0보다 커야합니다.");
        }
        return userRepository.getUser(userId);
    }
}
