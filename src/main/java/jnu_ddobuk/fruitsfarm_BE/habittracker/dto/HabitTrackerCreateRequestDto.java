package jnu_ddobuk.fruitsfarm_BE.habittracker.dto;

import java.time.LocalDate;

public record HabitTrackerCreateRequestDto(
        String type,
        String achievement,
        String motivation,
        LocalDate startDate
) {}