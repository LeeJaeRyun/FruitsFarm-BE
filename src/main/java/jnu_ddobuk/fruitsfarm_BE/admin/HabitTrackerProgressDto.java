package jnu_ddobuk.fruitsfarm_BE.admin;

import jnu_ddobuk.fruitsfarm_BE.habittracker.entity.HabitTracker;
import java.time.LocalDate;

public record HabitTrackerProgressDto(
        Long id,
        String type,
        String motivation,
        String achievement,
        LocalDate startDate,
        LocalDate endDate,
        int total, // 전체 개수
        int completedCount, // true인 개수
        double progressRate, // 진행률 
        boolean isCompleted // 완료 여부
) {
    public static HabitTrackerProgressDto from(HabitTracker t) {
        String progress = t.getProgress();
        int total = 0;
        int completedCount = 0;

        if (progress != null && !progress.isBlank()) {
            progress = progress.trim(); // 앞 뒤 공백 제거
            // "[", "]" 문자열 제거
            if (progress.startsWith("[")) {
                progress = progress.substring(1);
            }
            if (progress.endsWith("]")) {
                progress = progress.substring(0, progress.length() - 1);
            }

            // ","기준으로 문자열을 나눠서 배열로 만듦
            String[] tokens = progress.split(",");

            for (String token : tokens) {
                if (!token.isEmpty()) {
                    total++;
                    if (Boolean.parseBoolean(token)) {
                        completedCount++;
                    }
                }
            }
        }

        double progressRate = total > 0 ? (completedCount * 100.0 / total) : 0.0;
        boolean isCompleted = total > 0 && completedCount == total;

        return new HabitTrackerProgressDto(
                t.getId(),
                t.getType(),
                t.getMotivation(),
                t.getAchievement(),
                t.getStartDate(),
                t.getEndDate(),
                total,
                completedCount,
                progressRate,
                isCompleted
        );
    }

}
