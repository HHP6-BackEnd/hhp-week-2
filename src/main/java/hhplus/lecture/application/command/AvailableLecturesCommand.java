package hhplus.lecture.application.command;

import java.time.LocalDate;

public record AvailableLecturesCommand(
        Long userId,
        LocalDate currentDateTime
) {
}