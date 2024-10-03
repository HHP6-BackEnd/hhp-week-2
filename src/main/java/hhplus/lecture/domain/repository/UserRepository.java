package hhplus.lecture.domain.repository;


import hhplus.lecture.infrastructure.entity.User;

public interface UserRepository {
    User getUser(Long userId);
}
