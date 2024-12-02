package com.example.calendar.service;

import com.example.calendar.dto.ScheduleDeleteInput;
import com.example.calendar.dto.ScheduleDisplay;
import com.example.calendar.dto.ScheduleInput;
import com.example.calendar.dto.ScheduleUpdateInput;

import java.util.List;
import java.util.UUID;

public interface CalendarService {
    void createSchedule(ScheduleInput scheduleInput);
    List<ScheduleDisplay> findAllScheduleBySort();
    ScheduleDisplay findScheduleById(UUID id);
    void updateSchedule(ScheduleUpdateInput updateInput);
    void deleteSchedule(ScheduleDeleteInput deleteInput);
}
