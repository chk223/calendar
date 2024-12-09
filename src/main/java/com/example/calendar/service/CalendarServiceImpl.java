package com.example.calendar.service;

import com.example.calendar.domain.Schedule;
import com.example.calendar.domain.Writer;
import com.example.calendar.dto.*;
import com.example.calendar.exception.ApiException;
import com.example.calendar.exception.ErrorMessage;
import com.example.calendar.repository.ScheduleRepository;
import com.example.calendar.repository.WriterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CalendarServiceImpl implements CalendarService{
    private final ScheduleRepository scheduleRepository;
    private final WriterRepository writerRepository;

    @Override
    public void createSchedule(ScheduleInput scheduleInput) {
        Writer writer = findWriter(scheduleInput.getWriterId());
        Schedule schedule = new Schedule(scheduleInput.getTodo(),writer.getId(), scheduleInput.getPassword());
        scheduleRepository.create(schedule);
    }

    @Override
    public Page<ScheduleDisplay> findAllScheduleBySort(int page, int size) {
        int totalCountOfSchedule = scheduleRepository.countAll();
        if (page < 0 || size <= 0 || page * size >= totalCountOfSchedule) { // 페이지가 데이터 범위 밖일 경우
            return new Page<>(Collections.emptyList(), totalCountOfSchedule, page, size); // 빈 페이지 반환
        }
        PageRequest pageRequest = new PageRequest(page,size);
        List<Schedule> scheduleList = scheduleRepository.findAll(pageRequest.getStartPoint(), pageRequest.getSize());
        List<ScheduleDisplay> scheduleDisplayList = getScheduleDisplays(scheduleList);
        return new Page<>(scheduleDisplayList,totalCountOfSchedule,pageRequest.getPage()+1,pageRequest.getSize());
    }

    @Override
    public List<ScheduleDisplay> findScheduleByWriterId(UUID id) {
        List<Schedule> scheduleList = getSchedulesByWriterId(id);
        return getScheduleDisplays(scheduleList);
    }

    @Override
    public ScheduleDisplay findScheduleById(UUID id) {
        Schedule schedule = getScheduleById(id);
        return getScheduleDisplay(schedule);
    }

    @Override
    public void updateSchedule(ScheduleUpdateInput updateInput) {   //optional을 쓰면 형 변환이 강제화되기 때문에 null체크를 잊어버리지 않을 수 있음!
        Schedule schedule = findschedule(updateInput.getId());
        validatePassword(schedule, updateInput.getPassword());
        scheduleRepository.update(updateInput);
    }

    @Override
    public void deleteSchedule(ScheduleDeleteInput deleteInput) {
        Schedule schedule = findschedule(deleteInput.getId());
        validatePassword(schedule, deleteInput.getPassword());
        scheduleRepository.delete(deleteInput);
    }

    /**
     * 일정 id를 통해 특정 일정 조회
     * @param id 찾고자 하는 일정의 id
     * @return 해당 일정 객
     */
    private Schedule getScheduleById(UUID id) {
        return scheduleRepository.find(id)
                .orElseThrow(() -> {
                    log.warn("해당 id를 가진 할 일이 목록에 없습니다. 입력한 id={}", id);
                    ErrorMessage errorMessage = ErrorMessage.SCHEDULE_NOT_FOUND;
                    return new ApiException(errorMessage.getMessage(), errorMessage.getStatus());
                });
    }

    /**
     * 작성자 id를 통해 일정 조회. 결과가 없으면 예외를 던짐
     * @param id 작성자 id
     * @return 작성자가 작성한 일정 리스트
     */
    private List<Schedule> getSchedulesByWriterId(UUID id) {
        Writer writer = findWriter(id);
        return scheduleRepository.findByWriterId(writer.getId());
    }


    /**
     * 작성자의 id를 통해 작성자 조회
     * @param id 작성자 id
     * @return 작성자 객체
     */
    private Writer findWriter(UUID id) {
        return writerRepository.find(id)
                .orElseThrow(() -> {
                    log.warn("해당 id를 가진 할 일이 목록에 없습니다. 입력한 id={}", id);//
                    ErrorMessage errorMessage = ErrorMessage.WRITER_NOT_FOUND;
                    return new ApiException(errorMessage.getMessage(), errorMessage.getStatus());
                });
    }

    /**
     * schedule List를 scheduleDisplay List로 변환
     * @param scheduleList 주어진 리스트
     * @return scheduleDisplay List
     */
    private List<ScheduleDisplay> getScheduleDisplays(List<Schedule> scheduleList) {
        return scheduleList.stream()
                .map(this::getScheduleDisplay)  // schedule -> scheduleDisplay
                .collect(Collectors.toList());
    }

    /**
     * 일정 객체를 scheduleDisplay 객체로 변환
     * @param schedule 일정 객체
     * @return scheduleDisplay 객체
     */
    private ScheduleDisplay getScheduleDisplay(Schedule schedule) {
        Writer writer = findWriter(schedule.getWriterId());
        if(writer == null) {//writerId가 있는지 예외처리!
            ErrorMessage errorMessage = ErrorMessage.WRITER_NOT_FOUND;
            throw new ApiException(errorMessage.getMessage(), errorMessage.getStatus());
        }
        return new ScheduleDisplay(schedule.getTodo(), writer.getName(), schedule.getCreatedAt(), schedule.getUpdatedAt());
    }

    /**
     * id를 통해 일정을 조회
     * @param id 찾고자 하는 일정의 id
     * @return 찾은 일정
     */
    private Schedule findschedule(UUID id) {
        return scheduleRepository.find(id)
                .orElseThrow(() -> {
                    ErrorMessage errorMessage = ErrorMessage.SCHEDULE_NOT_FOUND;
                    log.warn("해당 id를 가진 할 일이 목록에 없습니다. 입력한 id={}", id);
                    return new ApiException(errorMessage.getMessage(), errorMessage.getStatus());
                });
    }

    /**
     * 삭제 대상의 비밀번호와 입력 받은 비밀번호를 비교하여 비밀번호 검증
     * @param schedule 수정/삭제 할 대상
     * @param password 입력 받은 비밀번호
     */
    private void validatePassword(Schedule schedule, String password) {
        if (!schedule.getPassword().equals(password)) {
            log.info("비밀번호 검증 실패! 입력한 비밀번호 = {}", password);
            ErrorMessage errorMessage = ErrorMessage.PASSWORD_IS_WRONG;
            throw new ApiException(errorMessage.getMessage(), errorMessage.getStatus());
        }
    }
}
