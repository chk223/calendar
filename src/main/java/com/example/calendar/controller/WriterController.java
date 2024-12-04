package com.example.calendar.controller;

import com.example.calendar.dto.WriterDeleteInput;
import com.example.calendar.dto.WriterDisplay;
import com.example.calendar.dto.WriterInput;
import com.example.calendar.dto.WriterUpdateInput;
import com.example.calendar.exception.ApiException;
import com.example.calendar.service.WriterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/writer")
public class WriterController {
    private final WriterService writerService;

    /**
     * 작성자 등록
     * @param writerInput 등록 할 작성자 정보
     */
    @PostMapping("/sign-up")
    public void signUp(@RequestBody @Valid WriterInput writerInput, BindingResult result)  {
        isFieldHasError(result);
        writerService.signUpWriter(writerInput);
    }

    /**
     * 모든 작성자 조회
     * @return 작성자 정보를 담은 리스트
     */
    @GetMapping("/all-writer")
    public List<WriterDisplay> getAllWriter () {
        return writerService.writerList();
    }

    /**
     * 고유 식별자를 통해 조회 한 특정 작성자 정보
     * @param id 고유 식별자
     * @return 특정 작성자 정보
     */
    @GetMapping
    public WriterDisplay getWriterInfo(UUID id) {
        return writerService.writerInfo(id);
    }

    /**
     * 작성자 정보 수정
     * @param updateInput 작성자 정보 수정을 위한 데이터
     */
    @PutMapping
    public void editInfo(@RequestBody @Valid WriterUpdateInput updateInput, BindingResult result)  {
        isFieldHasError(result);
        writerService.editWriterInfo(updateInput);
    }

    /**
     * 작성자 삭제
     * @param deleteInput 작성자 객체 삭제를 위한 데이터
     */
    @DeleteMapping
    public void withdraw(@RequestBody @Valid WriterDeleteInput deleteInput, BindingResult result) {
        isFieldHasError(result);
        writerService.withdraw(deleteInput);
    }

    private static void isFieldHasError(BindingResult result) {
        if (result.hasErrors()) {
            // 검증 실패 된 필드명을 예외에 포함해서 던짐
            List<String> errorMessages = result.getFieldErrors().stream()
                    .map(FieldError::getField)
                    .toList();
            throw new ApiException("검증 오류가 발생했습니다.", HttpStatus.BAD_REQUEST, errorMessages);
        }
    }

}
