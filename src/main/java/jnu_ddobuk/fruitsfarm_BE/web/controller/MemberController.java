package jnu_ddobuk.fruitsfarm_BE.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jnu_ddobuk.fruitsfarm_BE.domain.member.service.MemberService;
import jnu_ddobuk.fruitsfarm_BE.web.dto.common.CustomApiResponse;
import jnu_ddobuk.fruitsfarm_BE.web.dto.request.LoginRequestDto;
import jnu_ddobuk.fruitsfarm_BE.web.dto.request.SignUpRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 회원가입
    @Operation(summary = "회원가입", description = "닉네임과 비밀번호로 회원가입을 수행합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원가입 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터"),
    })
    @PostMapping("/v1/signup")
    public ResponseEntity<CustomApiResponse<String>> signup(@RequestBody SignUpRequestDto signUpRequestDto) {
        memberService.saveMember(signUpRequestDto);
        return ResponseEntity.ok(CustomApiResponse.ok("회원가입 성공"));
    }

    // 아이디 중복 체크
    @Operation(summary = "아이디 중복 체크", description = "아이디가 이미 사용 중인지 확인합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "사용 가능한 아이디입니다."),
            @ApiResponse(responseCode = "409", description = "이미 사용 중인 아이디입니다.")
    })
    @GetMapping("/v1/check-accountId")
    public ResponseEntity<CustomApiResponse<String>> checkAccount(@RequestParam String accountId) {
        memberService.isAccountIdDuplicate(accountId);
        return ResponseEntity.ok(CustomApiResponse.ok("사용 가능한 아이디입니다."));
    }

    // 로그인
    @Operation(summary = "로그인", description = "닉네임과 비밀번호로 로그인을 수행합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패")
    })
    @PostMapping("/v1/login")
    public ResponseEntity<CustomApiResponse<String>> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletRequest request) {
        memberService.login(loginRequestDto, request);
        return ResponseEntity.ok(CustomApiResponse.ok("로그인 성공"));
    }

    // 로그아웃
    @Operation(summary = "로그아웃", description = "로그아웃을 수행합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그아웃 성공"),
            @ApiResponse(responseCode = "400", description = "이미 로그아웃된 상태입니다.")
    })
    @PostMapping("/v1/logout")
    public ResponseEntity<CustomApiResponse<String>> logout(HttpServletRequest request) {
        memberService.logout(request);
        return ResponseEntity.ok(CustomApiResponse.ok("로그아웃 성공"));
    }

}