package jnu_ddobuk.fruitsfarm_BE.web.dto.common;

import jakarta.validation.constraints.NotNull;
import jnu_ddobuk.fruitsfarm_BE.common.exception.ErrorCode;
import lombok.Getter;

@Getter
public class ExceptionDto {
    @NotNull
    private final int code; //custom code를 보여줄 code 값

    @NotNull
    private final String message; //상황에 맞는 문장을 보여줄 message 값

    public ExceptionDto(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public static ExceptionDto of(ErrorCode errorCode) {
        return new ExceptionDto(errorCode);
    }
}
