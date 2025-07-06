package jnu_ddobuk.fruitsfarm_BE.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomException extends RuntimeException{
    private final ErrorCode errorCode;

    @Override
    public String getMessage() {
        return errorCode.getMessage();
    }
}