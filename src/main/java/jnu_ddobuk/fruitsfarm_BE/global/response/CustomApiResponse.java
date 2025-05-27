package jnu_ddobuk.fruitsfarm_BE.global.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import jnu_ddobuk.fruitsfarm_BE.global.exception.CustomException;
import jnu_ddobuk.fruitsfarm_BE.global.exception.ErrorCode;
import jnu_ddobuk.fruitsfarm_BE.global.exception.ExceptionDto;
import org.springframework.http.HttpStatus;

public record CustomApiResponse<T>(
        @JsonProperty("status_code") int statusCode,  // 상태 코드
        boolean success, // 성공 여부
        @Nullable T data, // 응답 데이터
        @Nullable ExceptionDto error // 에러 정보
) {
    // 성공 응답 생성
    public static <T> CustomApiResponse<T> ok(@Nullable T data) {
        return new CustomApiResponse<>(HttpStatus.OK.value(),
                true, data, null);
    }

    // 생성 성공 응답
    public static <T> CustomApiResponse<T> created(@Nullable T data) {
        return new CustomApiResponse<>(HttpStatus.CREATED.value(),
                true, data, null);
    }

    // 실패 응답 생성
    public static <T> CustomApiResponse<T> fail(CustomException e) {
        return new CustomApiResponse<>(e.getErrorCode().getHttpStatus().value(),
                false, null, ExceptionDto.of(e.getErrorCode()));
    }

    // 일반 Exception 처리
    public static <T> CustomApiResponse<T> fail(Exception e) {
        return new CustomApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                false, null, new ExceptionDto(ErrorCode.INTERNAL_SERVER_ERROR));
    }
}