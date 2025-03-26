package jnu_ddobuk.fruitsfarm_BE.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import jnu_ddobuk.fruitsfarm_BE.domain.member.service.MemberService;
import jnu_ddobuk.fruitsfarm_BE.domain.member.dto.LoginRequestDto;
import jnu_ddobuk.fruitsfarm_BE.domain.member.dto.SignUpRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    //회원가입
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignUpRequestDto signUpRequestDto) {
        memberService.saveMember(signUpRequestDto);
        return ResponseEntity.ok("회원가입 성공");
    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletRequest request) {
        memberService.login(loginRequestDto, request);

        return ResponseEntity.ok("로그인 성공");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        memberService.logout(request);
        return ResponseEntity.ok("로그아웃 성공");
    }

}