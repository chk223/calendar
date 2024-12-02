package com.example.calendar.service;

import com.example.calendar.domain.Schedule;
import com.example.calendar.dto.ScheduleDisplay;
import com.example.calendar.dto.ScheduleInput;
import com.example.calendar.repository.CalendarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CalendarServiceImpl implements CalendarService{
    private final CalendarRepository calendarRepository;

    public void createSchedule(ScheduleInput scheduleInput) {
        Schedule schedule = new Schedule(scheduleInput.getTodo(),scheduleInput.getName(),
                scheduleInput.getPassword());
        calendarRepository.create(schedule);
    }

    public List<ScheduleDisplay> findAllScheduleBySort() {
        List<Schedule> scheduleList = calendarRepository.findAll();
        return scheduleList.stream()
                .map(schedule -> new ScheduleDisplay(schedule.getTodo(), schedule.getName(), schedule.getCreatedAt(), schedule.getEditedAt()))  // 변환
                .sorted(Comparator.comparing(ScheduleDisplay::getEditedAt).reversed())  // 정렬
                .collect(Collectors.toList());
    }

    public ScheduleDisplay findScheduleById(UUID id) {
        Schedule schedule = calendarRepository.find(id);
        return new ScheduleDisplay(schedule.getTodo(), schedule.getName(), schedule.getCreatedAt(), schedule.getEditedAt());
    }
}
