package jnu_ddobuk.fruitsfarm_BE.domain.member.model.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_Id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String accountId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false) // Salt 필드 추가
    private String salt;

    public Member(String accountId, String password, String salt) {
        this.accountId = accountId;
        this.password = password;
        this.salt = salt;
    }

}
