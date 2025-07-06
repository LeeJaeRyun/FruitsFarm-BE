package jnu_ddobuk.fruitsfarm_BE.habittracker.controller;

import jakarta.servlet.http.HttpServletRequest;
import jnu_ddobuk.fruitsfarm_BE.global.response.CustomApiResponse;
import jnu_ddobuk.fruitsfarm_BE.habittracker.dto.*;
import jnu_ddobuk.fruitsfarm_BE.habittracker.entity.HabitTracker;
import jnu_ddobuk.fruitsfarm_BE.habittracker.service.HabitTrackerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    //해빗트래커 전체 조회
    @GetMapping("/v1/habit-trackers")
    public ResponseEntity<CustomApiResponse<List<HabitTrackerListResponseDto>>> getAllHabits(HttpServletRequest request) {
        List<HabitTrackerListResponseDto> responseDto = habitTrackerService.getAllHabitTrackers(request);
        return ResponseEntity.ok(CustomApiResponse.ok(responseDto));
    }

    //해빗트래커 단건 조회
    @GetMapping("/v1/habit-trackers/{habitTrackerId}")
    public ResponseEntity<CustomApiResponse<HabitTrackerDetailResponseDto>> getOneHabit(
            @PathVariable Long habitTrackerId,
            HttpServletRequest request
    ) {
        HabitTrackerDetailResponseDto responseDto = habitTrackerService.getOneHabitTracker(habitTrackerId,request);
        return ResponseEntity.ok(CustomApiResponse.ok(responseDto));
    }

    //해빗트래커 삭제
    @DeleteMapping("/v1/habit-trackers/{habitTrackerId}")
    public ResponseEntity<Void> deleteOneHabit(
            @PathVariable Long habitTrackerId,
            HttpServletRequest request
    ) {
        habitTrackerService.deleteHabitTracker(habitTrackerId,request);
        return ResponseEntity.noContent().build();
    }


}
