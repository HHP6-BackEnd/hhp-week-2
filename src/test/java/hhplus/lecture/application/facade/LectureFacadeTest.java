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

    @Test
    @DisplayName("1명이 동시에 5번 요청했을 때, 1번 빼고 모두 실패")
    void testApplyConcurrentOneUserFiveApply() throws Exception {

        // given
        List<CompletableFuture<Boolean>> futures = new ArrayList<>(); // Boolean을 반환하도록 수정

        for (int i = 0; i < 5; i++) {
            // 각 요청을 비동기적으로 실행
            CompletableFuture<Boolean> future = CompletableFuture.supplyAsync(() -> {
                try {
                    lectureFacade.applyLecture(new ApplyLectureCommand(1L, 2L));
                    return true; // 성공 시 true 반환
                } catch (Exception e) {
                    return false; // 실패 시 false 반환
                }
            });
            futures.add(future);
        }

        // 모든 CompletableFuture가 완료될 때까지 대기
        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        allOf.join(); // 예외가 발생하면 여기서 발생함

        // 성공/실패 확인
        long successCount = futures.stream()
                .filter(CompletableFuture::join) // true인 경우만 필터링
                .count();

        long failureCount = futures.size() - successCount;

        // 결과 출력
        System.out.println("성공: " + successCount + ", 실패: " + failureCount);

        // 검증
        assertThat(successCount).isEqualTo(1); // 성공한 수는 1이어야 함
        assertThat(failureCount).isEqualTo(4); // 실패한 수는 4이어야 함
    }

}
