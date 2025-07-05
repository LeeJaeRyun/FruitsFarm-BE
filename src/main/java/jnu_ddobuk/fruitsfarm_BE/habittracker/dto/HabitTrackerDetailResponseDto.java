package jnu_ddobuk.fruitsfarm_BE.habittracker.dto;

import jnu_ddobuk.fruitsfarm_BE.habittracker.entity.HabitTracker;

import java.time.LocalDate;

public record HabitTrackerDetailResponseDto(
        Long habitTrackerId,
        String type,
        String achievement,
        String motivation,
        LocalDate startDate,
        LocalDate endDate,
        String progress
) {
    public static HabitTrackerDetailResponseDto fromEntity(HabitTracker habitTracker) {
        return new HabitTrackerDetailResponseDto(
                habitTracker.getId(),
                habitTracker.getType(),
                habitTracker.getAchievement(),
                habitTracker.getMotivation(),
                habitTracker.getStartDate(),
                habitTracker.getEndDate(),
                habitTracker.getProgress()
        );
    }
}
