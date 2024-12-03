package com.example.calendar.service;

import com.example.calendar.domain.Schedule;
import com.example.calendar.dto.ScheduleDeleteInput;
import com.example.calendar.dto.ScheduleDisplay;
import com.example.calendar.dto.ScheduleInput;
import com.example.calendar.dto.ScheduleUpdateInput;
import com.example.calendar.repository.ScheduleRepository;
import com.example.calendar.repository.WriterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CalendarServiceImpl implements CalendarService{
    private final ScheduleRepository scheduleRepository;
    private final WriterRepository writerRepository;

    public void createSchedule(ScheduleInput scheduleInput) {
        Schedule schedule = new Schedule(scheduleInput.getTodo(),scheduleInput.getWriterId(),
                scheduleInput.getPassword());
        scheduleRepository.create(schedule);
    }

    public List<ScheduleDisplay> findAllScheduleBySort() {
        List<Schedule> scheduleList = scheduleRepository.findAll();
        if(scheduleList.isEmpty()) {
            log.warn("저장된 할 일이 없습니다!");
            return new ArrayList<>();
        }
        else return scheduleList.stream()
                .map(schedule -> new ScheduleDisplay(schedule.getTodo(), writerRepository.find(schedule.getWriterId()).getName(), schedule.getCreatedAt(), schedule.getUpdatedAt()))  // 변환
                .sorted(Comparator.comparing(ScheduleDisplay::getEditedAt).reversed())  // 정렬
                .collect(Collectors.toList());
    }

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
