package jnu_ddobuk.fruitsfarm_BE.global.exception;

import jnu_ddobuk.fruitsfarm_BE.global.response.CustomApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    //CustomException 처리
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CustomApiResponse<Void>> handleCustomException(CustomException e) {
        log.debug("CustomException 처리됨: {}", e.getMessage());
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(CustomApiResponse.fail(e));
    }

    //기타 예외 처리 (예상치 못한 오류)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomApiResponse<Void>> handleGlobalException(Exception e) {
        log.debug("예상치 못한 예외 처리됨: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(CustomApiResponse.fail(e));
    }

}
