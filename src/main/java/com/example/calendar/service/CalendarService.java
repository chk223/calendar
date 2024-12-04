package com.example.calendar.service;

import com.example.calendar.dto.*;

import java.util.List;
import java.util.UUID;

public interface CalendarService {
    /**
     * 일정 생성
     * @param scheduleInput 일정 정보(todo, writerId, password)
     */
    void createSchedule(ScheduleInput scheduleInput);

    /**
     * 페이징을 통해 나눈 범위 내의 모든 일정 반환
     * @param page 원하는 페이지 번호
     * @param size 각 페이지에 들어갈 객체 수
     * @return 페이지 범위 내의 데이터(ScheduleDisplay)
     */
    Page<ScheduleDisplay> findAllScheduleBySort(int page, int size);

    /**
     * 일정의 고유 식별자를 통해 일정 정보 조회
     * @param id 고유 식별자
     * @return ScheduleDisplay
     */
    ScheduleDisplay findScheduleById(UUID id);

    /**
     * 일정 정보 변경
     * @param updateInput 일정 정보 변경에 필요한 데이터(id,password,todo)
     */
    void updateSchedule(ScheduleUpdateInput updateInput);

    /**
     * 일정 삭제
     * @param deleteInput 일정 삭제에 필요한 데이터(id, password)
     */
    void deleteSchedule(ScheduleDeleteInput deleteInput);
}
