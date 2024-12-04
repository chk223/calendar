package com.example.calendar.repository;

import com.example.calendar.domain.Schedule;
import com.example.calendar.dto.ScheduleDeleteInput;
import com.example.calendar.dto.ScheduleUpdateInput;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ScheduleRepository {
    /**
     * 일정 생성
     * @param schedule 생성된 일정 객체 -> db저장
     */
    void create(Schedule schedule);

    /**
     * 모든 일정을 페이징하여 원하는 페이지의 일정을 리스트로 반환
     * @param startPoint 시작할 index
     * @param size 페이지의 크기
     * @return 페이징 된 일정 리스트
     */
    List<Schedule> findAll(int startPoint, int size);

    /**
     * 모든 일정 개수 반환
     * @return 모든 일정 개수
     */
    int countAll();

    /**
     * 고유 식별자를 통해 일정 조회
     * @param id 고유 식별자
     * @return 조회 한 일정
     */
    Optional<Schedule> find(UUID id);

    /**
     * 작성자 아이디를 통해 해당 작성자가 작성한 일정 모두 조회
     * @param id 작성자 아이디(고유 식별자)
     * @return 작성자가 작성한 일정 리스트
     */
    List<Schedule> findByWriterId(UUID id);

    /**
     * 일정 수정
     * @param updateInput 수정 할 일정의 정보(id,todo,password)
     */
    void update(ScheduleUpdateInput updateInput);

    /**
     * 일정 삭제
     * @param deleteInput 삭제 할 일정의 정보(id, password)
     */
    void delete(ScheduleDeleteInput deleteInput);

    /**
     * 테스트 코드를 위한 메모리 저장소 비우는 기능
     */
    void clearStorage();
}
