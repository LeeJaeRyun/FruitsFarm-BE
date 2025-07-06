package jnu_ddobuk.fruitsfarm_BE.habittracker.dto;

import jnu_ddobuk.fruitsfarm_BE.habittracker.entity.HabitTracker;

import java.time.LocalDate;

public record HabitTrackerListResponseDto(
        Long habitTrackerId,
        String type,
        String achievement,
        LocalDate startDate,
        LocalDate endDate
) {
    public static HabitTrackerListResponseDto fromEntity(HabitTracker habitTracker) {
        return new HabitTrackerListResponseDto(
                habitTracker.getId(),
                habitTracker.getType(),
                habitTracker.getAchievement(),
                habitTracker.getStartDate(),
                habitTracker.getEndDate()
        );
    }
}