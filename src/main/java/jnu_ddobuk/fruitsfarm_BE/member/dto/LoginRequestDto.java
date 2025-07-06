package jnu_ddobuk.fruitsfarm_BE.member.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
public class LoginRequestDto {
    private String accountId;
    private String password;
}

