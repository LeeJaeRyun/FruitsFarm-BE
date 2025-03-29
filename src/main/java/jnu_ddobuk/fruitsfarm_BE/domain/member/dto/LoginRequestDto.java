package jnu_ddobuk.fruitsfarm_BE.domain.member.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDto {
    private String accountId;
    private String password;
}

