package jnu_ddobuk.fruitsfarm_BE.habittracker.controller;

import jakarta.servlet.http.HttpServletRequest;
import jnu_ddobuk.fruitsfarm_BE.global.response.CustomApiResponse;
import jnu_ddobuk.fruitsfarm_BE.habittracker.dto.HabitTrackerCreateRequestDto;
import jnu_ddobuk.fruitsfarm_BE.habittracker.dto.HabitTrackerCreateResponseDto;
import jnu_ddobuk.fruitsfarm_BE.habittracker.dto.HabitTrackerUpdateRequestDto;
import jnu_ddobuk.fruitsfarm_BE.habittracker.dto.HabitTrackerUpdateResponseDto;
import jnu_ddobuk.fruitsfarm_BE.habittracker.entity.HabitTracker;
import jnu_ddobuk.fruitsfarm_BE.habittracker.service.HabitTrackerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class HabitTrackerController {

    private final HabitTrackerService habitTrackerService;

    //해빗트래커 생성
    @PostMapping("/v1/habit-trackers")
    public ResponseEntity<CustomApiResponse<HabitTrackerCreateResponseDto>> createHabit(
            @RequestBody HabitTrackerCreateRequestDto dto,
            HttpServletRequest request) {
        HabitTrackerCreateResponseDto responseDto = habitTrackerService.createHabit(dto, request);
        return ResponseEntity.ok(CustomApiResponse.ok(responseDto));
    }

    //해빗트래커 수정
    @PutMapping("/v1/habit-trackers/{habitTrackerId}")
    public ResponseEntity<CustomApiResponse<HabitTrackerUpdateResponseDto>> updateHabit(
            @PathVariable Long habitTrackerId,
            @RequestBody HabitTrackerUpdateRequestDto dto,
            HttpServletRequest request
    ) {
        Long updatedHabitTrackerId = habitTrackerService.updateHabit(habitTrackerId,dto,request);

        HabitTrackerUpdateResponseDto responseDto = new HabitTrackerUpdateResponseDto(
                updatedHabitTrackerId,
                "해빗트래커 수정 성공"
        );
        return ResponseEntity.ok(CustomApiResponse.ok(responseDto));
    }




}
