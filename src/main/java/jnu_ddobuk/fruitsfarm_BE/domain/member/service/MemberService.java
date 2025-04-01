package jnu_ddobuk.fruitsfarm_BE.domain.member.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jnu_ddobuk.fruitsfarm_BE.common.exception.CustomException;
import jnu_ddobuk.fruitsfarm_BE.common.exception.ErrorCode;
import jnu_ddobuk.fruitsfarm_BE.common.util.PasswordUtils;
import jnu_ddobuk.fruitsfarm_BE.common.constant.SessionConst;
import jnu_ddobuk.fruitsfarm_BE.domain.member.model.Member;
import jnu_ddobuk.fruitsfarm_BE.domain.member.repository.MemberRepository;
import jnu_ddobuk.fruitsfarm_BE.web.dto.request.LoginRequestDto;
import jnu_ddobuk.fruitsfarm_BE.web.dto.request.SignUpRequestDto;
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

//        //중복 아이디 체크
//        if (memberRepository.findByAccountId(signUpRequestDto.getAccountId()) != null) {
//            // 예외가 발생하는지 확인하기 위한 로그 추가
//            throw new CustomException(ErrorCode.DUPLICATE_ACCOUNT);
//        }

        // 비밀번호 암호화 (SHA-256 + Salt)
        String salt = PasswordUtils.generateSalt();
        String hashedPassword = PasswordUtils.hashPassword(signUpRequestDto.getPassword(), salt);

        // DB에 저장할 때 salt도 함께 저장해야 함 (salt 저장 방식c  필요)
        Member member = new Member(signUpRequestDto.getAccountId(), hashedPassword, salt);
        return memberRepository.save(member);
    }

    // 아이디 중복 체크
    public void isAccountIdDuplicate(String accountId) {
        if (memberRepository.findByAccountId(accountId) != null) {
            // 예외가 발생하는지 확인하기 위한 로그 추가
            log.debug("중복된 계정 아이디: {}", accountId);
            throw new CustomException(ErrorCode.DUPLICATE_ACCOUNT);
        }
    }

    //로그인
    public Member login(LoginRequestDto loginRequestDto, HttpServletRequest request) {
        Member member = memberRepository.findByAccountId(loginRequestDto.getAccountId());

        if (member == null) {
            throw new CustomException(ErrorCode.NOT_FOUND_MEMBER);
        }

        // DB에서 저장된 Salt 가져오는 로직 필요 (현재 Member 엔티티에 Salt 필드 없음)
        String storedSalt = member.getSalt();
        String hashedInputPassword = PasswordUtils.hashPassword(loginRequestDto.getPassword(), storedSalt);

        if (!hashedInputPassword.equals(member.getPassword())) {
            throw new CustomException(ErrorCode.INCORRECT_PASSWORD);
        }

        // 세션에 Member 객체 저장
        HttpSession session = request.getSession();
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