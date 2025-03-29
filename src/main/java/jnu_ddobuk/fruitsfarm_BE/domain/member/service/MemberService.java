package jnu_ddobuk.fruitsfarm_BE.domain.member.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jnu_ddobuk.fruitsfarm_BE.domain.common.PasswordUtils;
import jnu_ddobuk.fruitsfarm_BE.domain.common.SessionConst;
import jnu_ddobuk.fruitsfarm_BE.domain.member.model.entity.Member;
import jnu_ddobuk.fruitsfarm_BE.domain.member.repository.MemberRepository;
import jnu_ddobuk.fruitsfarm_BE.domain.member.dto.LoginRequestDto;
import jnu_ddobuk.fruitsfarm_BE.domain.member.dto.SignUpRequestDto;
import jnu_ddobuk.fruitsfarm_BE.domain.member.exception.AccountNotFoundException;
import jnu_ddobuk.fruitsfarm_BE.domain.member.exception.IncorrectPasswordException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    // 회원가입
    public Member saveMember(SignUpRequestDto signUpRequestDto) {

        if (memberRepository.findByAccountId(signUpRequestDto.getAccountId()) != null) {
            throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
        }

        // 비밀번호 암호화 (SHA-256 + Salt)
        String salt = PasswordUtils.generateSalt();
        String hashedPassword = PasswordUtils.hashPassword(signUpRequestDto.getPassword(), salt);

        // DB에 저장할 때 salt도 함께 저장해야 함 (salt 저장 방식 필요)
        Member member = new Member(signUpRequestDto.getAccountId(), hashedPassword, salt);
        return memberRepository.save(member);
    }

    //로그인
    public Member login(LoginRequestDto loginRequestDto, HttpServletRequest request) {
        Member member = memberRepository.findByAccountId(loginRequestDto.getAccountId());

        if (member == null) {
            throw new AccountNotFoundException("존재하지 않는 계정입니다.");
        }

        // DB에서 저장된 Salt 가져오는 로직 필요 (현재 Member 엔티티에 Salt 필드 없음)
        String storedSalt = member.getSalt();
        String hashedInputPassword = PasswordUtils.hashPassword(loginRequestDto.getPassword(), storedSalt);

        if (!hashedInputPassword.equals(member.getPassword())) {
            throw new IncorrectPasswordException("비밀번호가 틀렸습니다.");
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
            throw new IllegalStateException("이미 로그아웃된 상태입니다.");
        }
        session.invalidate();//세션 삭제
    }

}