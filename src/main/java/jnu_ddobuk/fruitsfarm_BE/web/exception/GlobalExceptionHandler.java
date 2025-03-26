package jnu_ddobuk.fruitsfarm_BE.web.exception;

import jnu_ddobuk.fruitsfarm_BE.domain.member.exception.AccountNotFoundException;
import jnu_ddobuk.fruitsfarm_BE.domain.member.exception.IncorrectPasswordException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //1. 회원을 찾을 수 없을 때 예외 처리
    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<String> handleAccountNotFoundException(AccountNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
    //회원이 존재하지 않는 경우 404 NOT FOUND 상태코드를 반환함.

    //2. 비밀번호가 틀렸을 때 예외 처리
    @ExceptionHandler(IncorrectPasswordException.class)
    public ResponseEntity<String> handleIncorrectPasswordException(IncorrectPasswordException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }
    //비밀번호가 틀렸을 경우 401 UNAUTHORIZED 상태코드를 반환함.

    //3. 기타 예외 처리 (예상치 못한 오류)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGlobalException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("예상치못한 오류입니다. 에러 메시지: " + ex.getMessage());
    }
    //예상하지 못한 예외가 발생하면 500 INTERNAL SERVER ERROR로 응답함.

}
