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
        Schedule schedule = new Schedule(scheduleInput.getTodo(),scheduleInput.getWriterId(),
                scheduleInput.getPassword());
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
        return new Page<>(scheduleDisplayList,totalCountOfSchedule,pageRequest.getPage(),pageRequest.getSize());
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
    @Override
    public ScheduleDisplay findScheduleById(UUID id) {
        Schedule schedule = Optional.ofNullable(scheduleRepository.find(id))
                .orElseThrow(() -> {
                    log.warn("해당 id를 가진 할 일이 목록에 없습니다. 입력한 id={}", id);
                    ErrorMessage errorMessage = ErrorMessage.SCHEDULE_NOT_FOUND;
                    return new ApiException(errorMessage.getMessage(), errorMessage.getStatus());
                });
        return getScheduleDisplay(schedule);
    }

    /**
     * schedule 객체를 scheduleDisplay로 변환
     * @param schedule
     * @return scheduleDisplay 객체
     */
    private ScheduleDisplay getScheduleDisplay(Schedule schedule) {
        Writer writer = writerRepository.find(schedule.getWriterId());
        if(writer == null) {//writerId가 있는지 예외처리!
            ErrorMessage errorMessage = ErrorMessage.WRITER_NOT_FOUND;
            throw new ApiException(errorMessage.getMessage(), errorMessage.getStatus());
        }
        return new ScheduleDisplay(schedule.getTodo(), writer.getName(), schedule.getCreatedAt(), schedule.getUpdatedAt());
    }

    @Override
    public void updateSchedule(ScheduleUpdateInput updateInput) {
        Schedule schedule = scheduleRepository.find(updateInput.getId());
        validatePassword(schedule, updateInput.getPassword());
        scheduleRepository.update(updateInput);
    }

    @Override
    public void deleteSchedule(ScheduleDeleteInput deleteInput) {
        Schedule schedule = scheduleRepository.find(deleteInput.getId());
        validatePassword(schedule, deleteInput.getPassword());
        scheduleRepository.delete(deleteInput);
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
