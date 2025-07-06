package jnu_ddobuk.fruitsfarm_BE.member.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jnu_ddobuk.fruitsfarm_BE.global.exception.CustomException;
import jnu_ddobuk.fruitsfarm_BE.global.exception.ErrorCode;
import jnu_ddobuk.fruitsfarm_BE.global.util.PasswordUtils;
import jnu_ddobuk.fruitsfarm_BE.global.constant.SessionConst;
import jnu_ddobuk.fruitsfarm_BE.member.entity.Member;
import jnu_ddobuk.fruitsfarm_BE.member.repository.MemberRepository;
import jnu_ddobuk.fruitsfarm_BE.member.dto.LoginRequestDto;
import jnu_ddobuk.fruitsfarm_BE.member.dto.SignUpRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    // 회원가입
    public Member saveMember(SignUpRequestDto signUpRequestDto) {

        //중복 아이디 체크
        if (memberRepository.findByAccountId(signUpRequestDto.getAccountId()) != null) {
            // 예외가 발생하는지 확인하기 위한 로그 추가
            throw new CustomException(ErrorCode.DUPLICATE_ACCOUNT);
        }

        // 비밀번호 암호화 (SHA-256 + Salt)
        String salt = PasswordUtils.generateSalt();
        String hashedPassword = PasswordUtils.hashPassword(signUpRequestDto.getPassword(), salt);

        // DB에 저장할 때 salt도 함께 저장해야 함 (salt 저장 방식 필요)
        Member member = new Member(signUpRequestDto.getAccountId(), hashedPassword, salt);
        return memberRepository.save(member);
    }

    // 아이디 중복 체크
    public void isAccountIdDuplicate(String accountId) {
        if (memberRepository.findByAccountId(accountId) != null) {
            throw new CustomException(ErrorCode.DUPLICATE_ACCOUNT);
        }
    }

    //로그인
    public Member login(LoginRequestDto loginRequestDto, HttpServletRequest request) {

        Member member = memberRepository.findByAccountId(loginRequestDto.getAccountId());

        //아이디로 회원찾고 없으면 예외 발생
        if (member == null) {
            throw new CustomException(ErrorCode.NOT_FOUND_MEMBER);
        }

        // DB에서 저장된 salt를 가져오고 이 salt값으로 사용자가 입력한 비밀번호를 바탕으로 SHA-256 해시를 수행함
        String storedSalt = member.getSalt();
        String hashedInputPassword = PasswordUtils.hashPassword(loginRequestDto.getPassword(), storedSalt);

        if (!hashedInputPassword.equals(member.getPassword())) {
            throw new CustomException(ErrorCode.INCORRECT_PASSWORD);
        }

        // 세션에 Member 객체 저장
        HttpSession session = request.getSession(); // 기존 세션 없을 경우 새로 만듦
        session.setAttribute(SessionConst.LOGIN_MEMBER, member);

        return member;
    }

    //로그아웃
    public void logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new CustomException(ErrorCode.ALREADY_LOGGED_OUT);
        }
        session.invalidate();//세션 삭제
    }

}