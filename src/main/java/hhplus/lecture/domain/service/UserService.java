package hhplus.lecture.domain.service;


import hhplus.lecture.infrastructure.entity.User;

public interface UserService {
    User getUser(Long userId);
}
