package jnu_ddobuk.fruitsfarm_BE.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // 400 Bad Request
    TEST_ERROR(10000, HttpStatus.BAD_REQUEST, "테스트 에러입니다."),
    ALREADY_LOGGED_OUT(40001, HttpStatus.BAD_REQUEST, "이미 로그아웃된 상태입니다."),

    // 401 Unauthorized
    INCORRECT_PASSWORD(40100, HttpStatus.UNAUTHORIZED, "비밀번호가 틀렸습니다."),

    // 404 Not Found
    NOT_FOUND_END_POINT(40400, HttpStatus.NOT_FOUND, "존재하지 않는 API입니다."),
    NOT_FOUND_MEMBER(40401, HttpStatus.NOT_FOUND, "존재하지 않는 계정입니다."),

    // 409 Conflict
    DUPLICATE_ACCOUNT(40900, HttpStatus.CONFLICT, "이미 사용 중인 계정입니다."),

    // 500 Internal Server Error
    INTERNAL_SERVER_ERROR(50000, HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류입니다.");

    private final int code; // 커스텀 에러 코드
    private final HttpStatus httpStatus; // HTTP 상태 코드
    private final String message; // 에러 메시지
}