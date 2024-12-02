package com.example.calendar.service;

import com.example.calendar.domain.Schedule;
import com.example.calendar.dto.ScheduleDisplay;
import com.example.calendar.dto.ScheduleInput;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public interface CalendarService {
    public void createSchedule(ScheduleInput scheduleInput);
    public List<ScheduleDisplay> findAllScheduleBySort();
    public ScheduleDisplay findScheduleById(UUID id);
}
