package com.example.calendar.service;

import com.example.calendar.dto.*;

import java.util.List;
import java.util.UUID;

public interface CalendarService {
    void createSchedule(ScheduleInput scheduleInput);
    Page<ScheduleDisplay> findAllScheduleBySort(int page, int size);
    ScheduleDisplay findScheduleById(UUID id);
    void updateSchedule(ScheduleUpdateInput updateInput);
    void deleteSchedule(ScheduleDeleteInput deleteInput);
}
