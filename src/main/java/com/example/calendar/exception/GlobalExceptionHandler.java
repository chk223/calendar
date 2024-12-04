package com.example.calendar.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException e) {
        // 검증 오류 메시지 추출
        // BindingResult에서 오류 목록을 가져옴
        List<String> errorMessages = e.getBindingResult().getAllErrors().stream()
                .map(error -> {
                    //FieldError일 경우
                    if (error instanceof FieldError) {
                        return error.getDefaultMessage();
                    }
                    // ObjectError일 경우
                    return error.getDefaultMessage();
                })
                .collect(Collectors.toList());

        // ErrorResponse 생성
        ErrorResponse errorResponse = new ErrorResponse(
                ErrorMessage.VALID_ERROR.getStatus().value(),
                ErrorMessage.VALID_ERROR.getMessage(),
                errorMessages
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
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
