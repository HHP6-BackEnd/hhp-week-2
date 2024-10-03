package hhplus.lecture.domain.service.Impl;

import hhplus.lecture.domain.repository.UserRepository;
import hhplus.lecture.infrastructure.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class SimpleUserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private SimpleUserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .userName("Test1")
                .build();
    }

    @Test
    @DisplayName("유효한 userId로 사용자 정보를 조회한다.")
    void getUser_WithValidUserId() {
        // given
        given(userRepository.getUser(anyLong())).willReturn(user);

        // when
        User result = userService.getUser(1L);

        // then
        assertThat(result).isEqualTo(user);
    }

    @Test
    @DisplayName("userId가 0이거나 음수일 경우 예외를 발생시킨다.")
    void getUser_WithInvalidUserId() {
        // when & then
        assertThatThrownBy(() -> userService.getUser(0L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("userId 는 0보다 커야합니다.");

        assertThatThrownBy(() -> userService.getUser(-1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("userId 는 0보다 커야합니다.");
    }
}
