package hhplus.lecture.application.facade;

import hhplus.lecture.application.command.ApplyLectureCommand;
import hhplus.lecture.domain.repository.ApplicationHistoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicLong;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class LectureFacadeTest {

    @Autowired
    private LectureFacade lectureFacade;

    @Autowired
    private ApplicationHistoryRepository applicationHistoryRepository;

    @Test
    @DisplayName("40명이 동시에 수강신청 시 30명 성공, 10명 실패")
    void testApplyConcurrent40User() throws Exception {

        // Given
        List<CompletableFuture<Boolean>> futures = new ArrayList<>();
        AtomicLong exceptionCount = new AtomicLong(0L);

        // When
        for (int i = 1; i <= 40; i++) {
            int finalI = i;
            CompletableFuture<Boolean> future = CompletableFuture.supplyAsync(() -> {
                try {
                    ApplyLectureCommand command = new ApplyLectureCommand(1L+ finalI, 2L);
                    lectureFacade.applyLecture(command); // 수강신청 시도
                    return true;
                } catch (Exception e) {
                    exceptionCount.getAndIncrement();
                    return false;
                }
            });
            futures.add(future);
        }

        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        allOf.get();

        // Then
        long successCount = futures.stream().filter(CompletableFuture::join).count();
        long failureCount = futures.size() - successCount;

        // 성공한 수강신청 수와 실패한 수 확인
        assertThat(successCount).isEqualTo(30);
        assertThat(failureCount).isEqualTo(10);
    }

}
