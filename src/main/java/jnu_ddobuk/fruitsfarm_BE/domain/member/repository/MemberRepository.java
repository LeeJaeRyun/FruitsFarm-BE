package jnu_ddobuk.fruitsfarm_BE.domain.member.repository;

import jnu_ddobuk.fruitsfarm_BE.domain.member.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByAccountId(String accountId);

}
