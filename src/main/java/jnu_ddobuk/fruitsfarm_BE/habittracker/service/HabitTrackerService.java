package jnu_ddobuk.fruitsfarm_BE.habittracker.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jnu_ddobuk.fruitsfarm_BE.global.constant.SessionConst;
import jnu_ddobuk.fruitsfarm_BE.global.exception.CustomException;
import jnu_ddobuk.fruitsfarm_BE.global.exception.ErrorCode;
import jnu_ddobuk.fruitsfarm_BE.habittracker.dto.HabitTrackerCreateRequestDto;
import jnu_ddobuk.fruitsfarm_BE.habittracker.dto.HabitTrackerCreateResponseDto;
import jnu_ddobuk.fruitsfarm_BE.habittracker.entity.HabitTracker;
import jnu_ddobuk.fruitsfarm_BE.habittracker.repository.HabitTrackerRepository;
import jnu_ddobuk.fruitsfarm_BE.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class HabitTrackerService {

    private final HabitTrackerRepository habitTrackerRepository;

    public HabitTrackerCreateResponseDto createHabit(HabitTrackerCreateRequestDto dto, HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        LocalDate startDate = dto.startDate();
        int period;

        switch (dto.type().toLowerCase()) {
            case "grape" -> period=10;
            case "watermelon" -> period=30;
            default -> throw new CustomException(ErrorCode.HABIT_TYPE_BAD_REQUEST);
        }

        LocalDate endDate = startDate.plusDays(period-1);

        StringBuilder progress = new StringBuilder("[");
        for (int i = 0; i < period; i++) {
            progress.append("false");
            if (i != period - 1) {
                progress.append(",");
            }
        }
        progress.append("]");

        HabitTracker habit = new HabitTracker(
                dto.type(),
                dto.achievement(),
                dto.motivation(),
                startDate,
                endDate,
                progress.toString(),
                loginMember
        );

        HabitTracker saved = habitTrackerRepository.save(habit);
        return new HabitTrackerCreateResponseDto(saved.getId());

    }
}
