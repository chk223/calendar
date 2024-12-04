package com.example.calendar.service;

import com.example.calendar.domain.Schedule;
import com.example.calendar.dto.*;
import com.example.calendar.repository.ScheduleRepository;
import com.example.calendar.repository.WriterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
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
                .map(schedule -> new ScheduleDisplay(schedule.getTodo(), writerRepository.find(schedule.getWriterId()).getName(), schedule.getCreatedAt(), schedule.getUpdatedAt()))  // schedule -> scheduleDisplay
                .collect(Collectors.toList());
    }
    @Override
    public ScheduleDisplay findScheduleById(UUID id) {
        Schedule schedule = scheduleRepository.find(id);
        if(schedule == null) {
            log.warn("해당 id를 가진 할 일이 목록에 없습니다. 입력한 id={}",id);
            return null;
        }
        else return new ScheduleDisplay(schedule.getTodo(), writerRepository.find(schedule.getWriterId()).getName(), schedule.getCreatedAt(), schedule.getUpdatedAt());
    }

    @Override
    public void updateSchedule(ScheduleUpdateInput updateInput) {
        Schedule schedule = scheduleRepository.find(updateInput.getId());
        if(schedule.getPassword().equals(updateInput.getPassword())) {
            scheduleRepository.update(updateInput);
        }
        else log.warn("수정 실패! 비밀번호가 잘못 입력되었습니다! 입력한 비밀번호 = {}",updateInput.getPassword());
    }

    @Override
    public void deleteSchedule(ScheduleDeleteInput deleteInput) {
        Schedule schedule = scheduleRepository.find(deleteInput.getId());
        if(schedule.getPassword().equals(deleteInput.getPassword())) {
            scheduleRepository.delete(deleteInput);
        }
        else log.warn("수정 실패! 비밀번호가 잘못 입력되었습니다! 입력한 비밀번호 = {}",deleteInput.getPassword());
    }
}
