package com.example.calendar.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorMessage {
    PASSWORD_IS_WRONG("비밀번호 입력이 잘못되었습니다.", HttpStatus.UNAUTHORIZED),
    SCHEDULE_NOT_FOUND("선택한 정보를 찾을 수 없습니다.", HttpStatus.UNPROCESSABLE_ENTITY),
    BLANK_INPUT("입력 값은 비어있을 수 없습니다.", HttpStatus.BAD_REQUEST),
    WRITER_NOT_FOUND("입력 받은 id에 해당하는 작성자 정보가 없습니다",HttpStatus.UNPROCESSABLE_ENTITY),
    VALID_ERROR("입력 값이 잘못되었습니다.",HttpStatus.BAD_REQUEST);
    private final String message;
    private final HttpStatus status;

    ErrorMessage(String message, HttpStatus status) {
        this.message = message;
        this.status=status;
    }
}
