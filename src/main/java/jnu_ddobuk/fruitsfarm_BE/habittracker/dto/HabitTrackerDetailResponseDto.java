package jnu_ddobuk.fruitsfarm_BE.habittracker.dto;

import java.time.LocalDate;

public record HabitTrackerDetailResponseDto(
        Long habitTrackerId,
        String type,
        String achievement,
        String motivation,
        LocalDate startDate,
        LocalDate endDate,
        String progress
) {}
