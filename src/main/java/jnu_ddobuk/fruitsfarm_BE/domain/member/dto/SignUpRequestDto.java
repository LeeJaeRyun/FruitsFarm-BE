package jnu_ddobuk.fruitsfarm_BE.domain.member.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequestDto {
    private String accountId;
    private String password;
}
