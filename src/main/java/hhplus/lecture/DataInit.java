package hhplus.lecture;

import hhplus.lecture.infrastructure.entity.ApplicationSchedule;
import hhplus.lecture.infrastructure.entity.Lecture;
import hhplus.lecture.infrastructure.entity.User;
import hhplus.lecture.infrastructure.jparepository.ApplicationScheduleJpaRepository;
import hhplus.lecture.infrastructure.jparepository.LectureJpaRepository;
import hhplus.lecture.infrastructure.jparepository.UserJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DataInit implements CommandLineRunner {

    private final UserJpaRepository userJpaRepository;
    private final LectureJpaRepository lectureJpaRepository;
    private final ApplicationScheduleJpaRepository applicationScheduleJpaRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        for (int i = 0; i < 40 ;i++) {
            userJpaRepository.save(new User(1L+i, "User" + i));
        }

        Lecture lecture1 = Lecture.builder().lectureTitle("tdd1").lectureDescription("tddTest1").lecturePresenter("teacher1").build();
        Lecture lecture2 = Lecture.builder().lectureTitle("tdd2").lectureDescription("tddTest2").lecturePresenter("teacher2").build();
        Lecture lecture3 = Lecture.builder().lectureTitle("tdd3").lectureDescription("tddTest3").lecturePresenter("teacher3").build();
        Lecture lecture4 = Lecture.builder().lectureTitle("tdd4").lectureDescription("tddTest4").lecturePresenter("teacher4").build();
        Lecture lecture5 = Lecture.builder().lectureTitle("tdd5").lectureDescription("tddTest5").lecturePresenter("teacher5").build();
        Lecture lecture6 = Lecture.builder().lectureTitle("tdd6").lectureDescription("tddTest6").lecturePresenter("teacher6").build();

        lectureJpaRepository.save(lecture1);
        lectureJpaRepository.save(lecture2);
        lectureJpaRepository.save(lecture3);
        lectureJpaRepository.save(lecture4);
        lectureJpaRepository.save(lecture5);
        lectureJpaRepository.save(lecture6);

        applicationScheduleJpaRepository.save(ApplicationSchedule.builder().lecture(lecture1).applicationDeadLine(LocalDate.now().minusDays(1)).build());
        applicationScheduleJpaRepository.save(ApplicationSchedule.builder().lecture(lecture2).applicationDeadLine(LocalDate.now()).build());
        applicationScheduleJpaRepository.save(ApplicationSchedule.builder().lecture(lecture3).applicationDeadLine(LocalDate.now().plusDays(1)).build());
        applicationScheduleJpaRepository.save(ApplicationSchedule.builder().lecture(lecture4).applicationDeadLine(LocalDate.now().plusDays(2)).build());
        applicationScheduleJpaRepository.save(ApplicationSchedule.builder().lecture(lecture5).applicationDeadLine(LocalDate.now().plusDays(10)).build());
        applicationScheduleJpaRepository.save(ApplicationSchedule.builder().lecture(lecture6).applicationDeadLine(LocalDate.now().plusDays(1)).build());

    }
}
