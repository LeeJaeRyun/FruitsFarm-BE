package jnu_ddobuk.fruitsfarm_BE.domain.member.repository;

import jnu_ddobuk.fruitsfarm_BE.domain.member.model.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    //nickname으로 회원 조회
    Member findByNickname(String nickname);

}
