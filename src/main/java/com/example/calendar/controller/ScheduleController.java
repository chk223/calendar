package com.example.calendar.controller;

import com.example.calendar.dto.*;
import com.example.calendar.exception.ApiException;
import com.example.calendar.service.CalendarService;
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
@RequestMapping("/schedule")
@Slf4j
public class ScheduleController {
    private final CalendarService calendarService;

    /**
     * 일정 등록
     * @param scheduleInput 일정 등록을 위한 정보
     */
    @PostMapping
    public void addSchedule(@RequestBody @Valid ScheduleInput scheduleInput, BindingResult result) {
        isFieldHasError(result);
        log.info("input = {} " , scheduleInput);
        calendarService.createSchedule(scheduleInput);
    }


    /**
     * 모든 일정 조회 - 페이지
     * @param page 원하는 페이지
     * @param size 페이지에 들어가는 객체 수
     * @return 페이징 된 일정들의 정보
     */
    @GetMapping("/all-schedule")
    public Page<ScheduleDisplay> getAllSchedule(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        return calendarService.findAllScheduleBySort(page-1, size);
    }

    /**
     * 작성자의 고유 식별자를 통해 해당 작성자가 작성한 일정을 모두 조회
     * @param id 작성자 아이디(고유 식별자)
     * @return 작성자가 작성한 일정 리스트
     */
    @GetMapping("/schedule-from-writer")
    public List<ScheduleDisplay> getScheduleByWriterId(UUID id) {
        return calendarService.findScheduleByWriterId(id);
    }

    /**
     * 고유 식별자를 통해 특정 일정 조회
     * @param id 고유 식별자
     * @return 조회 한 일정 정보
     */
    @GetMapping
    public ScheduleDisplay getSchedule(UUID id) {
        return calendarService.findScheduleById(id);
    }



    /**
     * 일정 수정
     * @param updateInput 수정 할 일정의 정보
     */
    @PutMapping
    public void updateSchedule(@RequestBody @Valid ScheduleUpdateInput updateInput, BindingResult result) {
        isFieldHasError(result);
        calendarService.updateSchedule(updateInput);
    }

    /**
     * 일정 삭제
     * @param deleteInput 삭제 할 일정의 정보
     */
    @DeleteMapping
    public void deleteSchedule(@RequestBody @Valid ScheduleDeleteInput deleteInput, BindingResult result) {
        isFieldHasError(result);
        calendarService.deleteSchedule(deleteInput);
    }

    /**
     * 필드 값의 오류 여부 검증
     * @param result 필드 값을 검증 한 결과를 갖는 객체
     */
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
