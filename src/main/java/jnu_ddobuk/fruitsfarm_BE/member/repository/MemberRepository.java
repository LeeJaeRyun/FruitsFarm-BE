package jnu_ddobuk.fruitsfarm_BE.member.repository;

import jnu_ddobuk.fruitsfarm_BE.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByAccountId(String accountId);

}
