package hhplus.lecture.interfaces.controller;

import hhplus.lecture.application.command.ApplicationHistoriesCommand;
import hhplus.lecture.application.command.ApplyLectureCommand;
import hhplus.lecture.application.command.AvailableLecturesCommand;
import hhplus.lecture.application.facade.LectureFacade;
import hhplus.lecture.application.info.ApplicationHistoriesInfo;
import hhplus.lecture.application.info.ApplyLectureInfo;
import hhplus.lecture.application.info.AvailableLecturesInfo;
import hhplus.lecture.infrastructure.entity.ApplicationHistory;
import hhplus.lecture.infrastructure.entity.Lecture;
import hhplus.lecture.infrastructure.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = LectureController.class)
@AutoConfigureMockMvc
class LectureControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LectureFacade lectureFacade;

    private ApplyLectureInfo applyLectureInfo;

    @BeforeEach
    void setUp() {
        User user = User.builder()
                .userName("TEST1")
                .build();

        Lecture lecture = Lecture.builder()
                .lectureId(1L)
                .lectureTitle("TEST1")
                .lectureDescription("TEST TEST 1")
                .lecturePresenter("Teacher1")
                .build();

        applyLectureInfo = new ApplyLectureInfo(new ApplicationHistory(user, lecture));
    }

    @Test
    @DisplayName("특정 유저의 특강 신청")
    void testApplyLecture() throws Exception {
        // given
        long userId = 1L;
        long lectureId = 1L;
        given(lectureFacade.applyLecture(new ApplyLectureCommand(userId, lectureId)))
                .willReturn(applyLectureInfo);

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/lecture/apply/{userId}/{lectureId}", userId, lectureId)
        );

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.lectureId").value(lectureId))
                .andDo(print());
    }

    @Test
    @DisplayName("특정 유저의 신청 가능한 특강 목록 조회")
    void testGetAvailableLectures() throws Exception {
        // given
        long userId = 1L;
        List<AvailableLecturesInfo> availableLectures = Collections.emptyList();
        given(lectureFacade.getAvailableLectures(new AvailableLecturesCommand(userId, LocalDate.now())))
                .willReturn(availableLectures);

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/lecture/lectures/{userId}", userId)
        );

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("특정 유저의 특강 신청 히스토리 조회")
    void testGetApplicationHistories() throws Exception {
        // given
        long userId = 1L;
        List<ApplicationHistoriesInfo> applicationHistories = Collections.emptyList();
        given(lectureFacade.getApplicationHistories(new ApplicationHistoriesCommand(userId)))
                .willReturn(applicationHistories);

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/lecture/histories/{userId}", userId)
        );

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print());
    }

}
