package com.example.calendar.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
/**
 * API 예외 처리(커스텀)
 */
public class GlobalExceptionHandler {
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(ApiException e) {
        List<String> errorField = e.getErrorField();
        if(errorField != null && !errorField.isEmpty()) {// 검증 오류 처리
            int errorCode = 0;
            String errorMessage = "입력 값이 잘못되었습니다.";
            if(errorField.contains("id")) errorCode = 101;
            else if(errorField.contains("password")) errorCode = 102;
            else if(errorField.contains("email")) errorCode = 103;
            else errorCode = 100;
            ErrorResponse errorResponse = new ErrorResponse(e.getStatus().value(), errorMessage, errorCode);
            return new ResponseEntity<>(errorResponse,e.getStatus());
        }
        // 일반 API 오류 처리
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
