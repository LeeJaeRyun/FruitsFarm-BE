package jnu_ddobuk.fruitsfarm_BE.habittracker.dto;

import jnu_ddobuk.fruitsfarm_BE.habittracker.entity.HabitTracker;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public record HabitTrackerDetailResponseDto(
        Long habitTrackerId,
        String type,
        String achievement,
        String motivation,
        LocalDate startDate,
        LocalDate endDate,
        String progress,
        int currentDate
) {
    public static HabitTrackerDetailResponseDto fromEntity(HabitTracker habitTracker) {
        int currentDate = calculateCurrentDate(habitTracker.getStartDate());

        return new HabitTrackerDetailResponseDto(
                habitTracker.getId(),
                habitTracker.getType(),
                habitTracker.getAchievement(),
                habitTracker.getMotivation(),
                habitTracker.getStartDate(),
                habitTracker.getEndDate(),
                habitTracker.getProgress(),
                currentDate
        );
    }

    private static int calculateCurrentDate(LocalDate startDate) {
        LocalDate today = LocalDate.now();
        long days = ChronoUnit.DAYS.between(startDate, today);
        return (int)days + 1;
    }

}
