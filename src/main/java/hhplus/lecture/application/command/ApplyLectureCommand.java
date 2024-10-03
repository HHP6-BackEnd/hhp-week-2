package hhplus.lecture.application.command;

public record ApplyLectureCommand(
        Long userId,
        Long lectureId
) {
}