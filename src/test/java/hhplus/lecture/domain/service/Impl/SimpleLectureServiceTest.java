package hhplus.lecture.domain.service.Impl;

import hhplus.lecture.domain.repository.LectureRepository;
import hhplus.lecture.infrastructure.entity.Lecture;
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
class SimpleLectureServiceTest {

    @Mock
    private LectureRepository lectureRepository;

    @InjectMocks
    private SimpleLectureService lectureService;

    private Lecture lecture;

    @BeforeEach
    void setUp() {
        lecture = Lecture.builder()
                .lectureTitle("Lecture 1")
                .lectureDescription("TEST Lecture 1")
                .lecturePresenter("Teacher 1")
                .build();
    }

    @Test
    @DisplayName("유효한 lectureId로 강의 정보를 조회한다.")
    void getLecture_WithValidLectureId() {
        // given
        given(lectureRepository.getLectureById(anyLong())).willReturn(lecture);

        // when
        Lecture result = lectureService.getLecture(1L);

        // then
        assertThat(result).isEqualTo(lecture);
    }

    @Test
    @DisplayName("lectureId가 0이거나 음수일 경우 예외를 발생시킨다.")
    void getLecture_WithInvalidLectureId() {
        // when & then
        assertThatThrownBy(() -> lectureService.getLecture(0L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("lectureId 는 0보다 커야합니다.");

        assertThatThrownBy(() -> lectureService.getLecture(-1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("lectureId 는 0보다 커야합니다.");
    }
}
