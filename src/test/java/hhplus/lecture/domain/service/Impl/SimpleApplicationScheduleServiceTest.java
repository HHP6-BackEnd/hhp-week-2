package hhplus.lecture.domain.service.Impl;

import hhplus.lecture.domain.repository.ApplicationScheduleRepository;
import hhplus.lecture.infrastructure.entity.ApplicationSchedule;
import hhplus.lecture.infrastructure.entity.Lecture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class SimpleApplicationScheduleServiceTest {

    @Mock
    private ApplicationScheduleRepository applicationScheduleRepository;

    @InjectMocks
    private SimpleApplicationScheduleService applicationScheduleService;

    private ApplicationSchedule applicationSchedule;
    private Lecture lecture;

    @BeforeEach
    void setUp() {
        lecture = Lecture.builder()
                .lectureId(1L)
                .lectureTitle("Lecture 1")
                .lectureDescription("TEST Lecture 1")
                .lecturePresenter("Teacher 1")
                .build();

        applicationSchedule = ApplicationSchedule.builder()
                .lecture(lecture)
                .currentApplicationCount(0L)
                .maxApplicationCount(30L)
                .applicationDeadLine(LocalDate.now().plusDays(1))
                .build();
    }

    @Test
    @DisplayName("유효한 강의로 신청 스케줄을 조회한다.")
    void getLectureApplicationSchedule_WithValidLecture() {
        // given
        given(applicationScheduleRepository.getLectureApplicationSchedule(1L)).willReturn(applicationSchedule);

        // when
        ApplicationSchedule result = applicationScheduleService.getLectureApplicationSchedule(lecture);

        // then
        assertThat(result).isEqualTo(applicationSchedule);
    }

    @Test
    @DisplayName("정원 초과일 경우 예외를 발생시킨다.")
    void getLectureApplicationSchedule_ExceededCapacity() {
        // given
        ApplicationSchedule applicationSchedule2 = ApplicationSchedule.builder()
                .lecture(lecture)
                .currentApplicationCount(31L) // 정원 초과
                .maxApplicationCount(30L)
                .applicationDeadLine(LocalDate.now().plusDays(1))
                .build();

        given(applicationScheduleRepository.getLectureApplicationSchedule(1L)).willReturn(applicationSchedule2);

        // when & then
        assertThatThrownBy(() -> applicationScheduleService.getLectureApplicationSchedule(lecture))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("강의 신청이 마감되었습니다.");  // 정원 초과
    }

    @Test
    @DisplayName("신청 기한이 지났을 경우 예외를 발생시킨다.")
    void getLectureApplicationSchedule_DeadlinePassed() {
        // given
        Lecture lecture2 = Lecture.builder()
                .lectureId(2L)
                .lectureTitle("Lecture 2")
                .lectureDescription("TEST Lecture 2")
                .lecturePresenter("Teacher 2")
                .build();

        ApplicationSchedule applicationSchedule2 = ApplicationSchedule.builder()
                .lecture(lecture2)
                .currentApplicationCount(0L)
                .maxApplicationCount(30L)
                .applicationDeadLine(LocalDate.now().minusDays(1)) // 기한 초과
                .build();

        // given
        given(applicationScheduleRepository.getLectureApplicationSchedule(2L)).willReturn(applicationSchedule2);

        // when & then
        assertThatThrownBy(() -> applicationScheduleService.getLectureApplicationSchedule(lecture2))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("강의 신청 기간이 지났습니다.");  // 기한 초과
    }

    @Test
    @DisplayName("신청한 강의 ID 목록이 비어있을 경우 모든 강의 조회.")
    void getAvailableLectures_WithEmptyAppliedLectureIds() {
        // given
        given(applicationScheduleRepository.getAllLectures()).willReturn(List.of(applicationSchedule));

        // when
        List<Lecture> result = applicationScheduleService.getAvailableLectures(Collections.emptyList());

        // then
        assertThat(result).containsExactly(lecture);
    }

    @Test
    @DisplayName("신청한 강의 ID 목록으로 강의를 조회.")
    void getAvailableLectures_WithAppliedLectureIds() {
        // given
        List<Long> appliedLectureIds = List.of(2L);
        given(applicationScheduleRepository.getAvailableLectures(appliedLectureIds)).willReturn(List.of(applicationSchedule));

        // when
        List<Lecture> result = applicationScheduleService.getAvailableLectures(appliedLectureIds);

        // then
        assertThat(result).containsExactly(lecture);
    }

    @Test
    @DisplayName("신청 가능한 강의가 없을 경우 예외를 발생시킨다.")
    void getAvailableLectures_NoAvailableLectures() {
        // given
        List<Long> appliedLectureIds = List.of(1L);
        given(applicationScheduleRepository.getAvailableLectures(appliedLectureIds)).willReturn(Collections.emptyList());

        // when & then
        assertThatThrownBy(() -> applicationScheduleService.getAvailableLectures(appliedLectureIds))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("현재 수강신청 가능한 과목이 없습니다.");
    }
}
