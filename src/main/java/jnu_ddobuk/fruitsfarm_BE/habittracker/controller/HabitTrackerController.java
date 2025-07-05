package jnu_ddobuk.fruitsfarm_BE.habittracker.controller;

import jakarta.servlet.http.HttpServletRequest;
import jnu_ddobuk.fruitsfarm_BE.global.response.CustomApiResponse;
import jnu_ddobuk.fruitsfarm_BE.habittracker.dto.HabitTrackerCreateRequestDto;
import jnu_ddobuk.fruitsfarm_BE.habittracker.dto.HabitTrackerCreateResponseDto;
import jnu_ddobuk.fruitsfarm_BE.habittracker.entity.HabitTracker;
import jnu_ddobuk.fruitsfarm_BE.habittracker.service.HabitTrackerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class HabitTrackerController {

    private final HabitTrackerService habitTrackerService;

    @PostMapping("/v1/habit-trackers")
    public ResponseEntity<CustomApiResponse<HabitTrackerCreateResponseDto>> createHabit(
            @RequestBody HabitTrackerCreateRequestDto dto,
            HttpServletRequest request) {
        HabitTrackerCreateResponseDto responseDto = habitTrackerService.createHabit(dto, request);
        return ResponseEntity.ok(CustomApiResponse.ok(responseDto));
    }


}
