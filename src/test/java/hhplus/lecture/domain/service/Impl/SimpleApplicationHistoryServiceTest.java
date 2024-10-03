package hhplus.lecture.domain.service.Impl;

import hhplus.lecture.domain.repository.ApplicationHistoryRepository;
import hhplus.lecture.infrastructure.entity.ApplicationHistory;
import hhplus.lecture.infrastructure.entity.Lecture;
import hhplus.lecture.infrastructure.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SimpleApplicationHistoryServiceTest {

    @Mock
    private ApplicationHistoryRepository applicationHistoryRepository;

    @InjectMocks
    private SimpleApplicationHistoryService applicationHistoryService;

    private User user;
    private Lecture lecture;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .userName("TEST1")
                .build();
        lecture = Lecture.builder()
                .lectureId(1L)
                .lectureTitle("TEST1")
                .lectureDescription("TEST TEST 1")
                .lecturePresenter("Teacher1")
                .build();
    }

    @Test
    @DisplayName("신청 기록을 저장한다.")
    void saveApplicationHistory() {
        // given
        ApplicationHistory expectedHistory = ApplicationHistory.builder()
                .user(user)
                .lecture(lecture)
                .build();
        when(applicationHistoryRepository.saveApplicationHistory(any())).thenReturn(expectedHistory);

        // when
        ApplicationHistory result = applicationHistoryService.saveApplicationHistory(user, lecture);

        // then
        assertThat(result).isEqualTo(expectedHistory);
        verify(applicationHistoryRepository, times(1)).saveApplicationHistory(any());
    }

    @Test
    @DisplayName("사용자의 신청한 강의 이력을 조회한다.")
    void getUserAppliedLectureHistories() {
        // given
        List<ApplicationHistory> histories = Collections.singletonList(
                ApplicationHistory.builder().user(user).lecture(lecture).build()
        );
        when(applicationHistoryRepository.getUserApplicationHistories(user)).thenReturn(histories);

        // when
        List<ApplicationHistory> result = applicationHistoryService.getUserAppliedLectureHistories(user);

        // then
        assertThat(result).isEqualTo(histories);
    }

    @Test
    @DisplayName("사용자의 신청한 강의 ID 목록을 조회한다.")
    void getUserAppliedLectureIds() {
        // given
        ApplicationHistory history = ApplicationHistory.builder().user(user).lecture(lecture).build();
        when(applicationHistoryRepository.getUserApplicationHistories(user)).thenReturn(Collections.singletonList(history));

        // when
        List<Long> result = applicationHistoryService.getUserAppliedLectureIds(user);

        // then
        assertThat(result).containsExactly(lecture.getLectureId());
    }
}
