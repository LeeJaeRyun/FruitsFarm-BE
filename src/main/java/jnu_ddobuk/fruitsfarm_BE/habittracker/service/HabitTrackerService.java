package jnu_ddobuk.fruitsfarm_BE.habittracker.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jnu_ddobuk.fruitsfarm_BE.global.constant.SessionConst;
import jnu_ddobuk.fruitsfarm_BE.global.exception.CustomException;
import jnu_ddobuk.fruitsfarm_BE.global.exception.ErrorCode;
import jnu_ddobuk.fruitsfarm_BE.habittracker.dto.*;
import jnu_ddobuk.fruitsfarm_BE.habittracker.entity.HabitTracker;
import jnu_ddobuk.fruitsfarm_BE.habittracker.repository.HabitTrackerRepository;
import jnu_ddobuk.fruitsfarm_BE.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HabitTrackerService {

    private final HabitTrackerRepository habitTrackerRepository;

    @Transactional
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

    @Transactional
    public Long updateHabit(Long habitTrackerId, HabitTrackerUpdateRequestDto dto, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        HabitTracker habitTracker = habitTrackerRepository.findById(habitTrackerId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_HABIT_TRACKER));

        //로그인한 사용자와 habitTracker작성자가 일치하는지 확인
        if (!habitTracker.getMember().getId().equals(loginMember.getId())) {
            throw new CustomException(ErrorCode.FORBIDDEN);
        }

        habitTracker.updateProgress(dto.progress());
        return habitTracker.getId();
    }

    @Transactional(readOnly = true)
    public List<HabitTrackerListResponseDto> getAllHabitTrackers(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        // 본인 habitTracker만 조회
        List<HabitTracker> habitTrackers = habitTrackerRepository.findByMemberId(loginMember.getId());

        return habitTrackers.stream()
                .map(HabitTrackerListResponseDto::fromEntity)
                .toList();
    }


    public HabitTrackerDetailResponseDto getOneHabitTracker(Long habitTrackerId, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        HabitTracker habitTracker = habitTrackerRepository.findById(habitTrackerId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_HABIT_TRACKER));

        //본인 소유인지 확인
        if (!habitTracker.getMember().getId().equals(loginMember.getId())) {
            throw new CustomException(ErrorCode.FORBIDDEN);
        }

        return HabitTrackerDetailResponseDto.fromEntity(habitTracker);
    }

    public void deleteHabitTracker(Long habitTrackerId, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        habitTrackerRepository.findById(habitTrackerId).ifPresent(habitTracker -> {
            if (habitTracker.getMember().getId().equals(loginMember.getId())) {
                habitTrackerRepository.delete(habitTracker);
            }
        });

    }
}
