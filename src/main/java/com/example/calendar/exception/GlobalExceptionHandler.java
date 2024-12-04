package com.example.calendar.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
/**
 * API 예외 처리(커스텀)
 */
public class GlobalExceptionHandler {
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(ApiException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getStatus().value(), e.getMessage());
        return new ResponseEntity<>(errorResponse,e.getStatus());
    }

    /**
     * 그 외의 예외 처리
     * @param e 잡은 예외 객체
     * @return 에외 던지기
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception e) {
        ErrorResponse errorResponse = new ErrorResponse(500, "예상치 못한 오류가 발생했습니다.");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
