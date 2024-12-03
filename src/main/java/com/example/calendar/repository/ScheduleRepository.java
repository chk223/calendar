package com.example.calendar.repository;

import com.example.calendar.domain.Schedule;
import com.example.calendar.dto.ScheduleDeleteInput;
import com.example.calendar.dto.ScheduleUpdateInput;

import java.util.List;
import java.util.UUID;

public interface ScheduleRepository {
    void create(Schedule schedule);
    List<Schedule> findAll();
    Schedule find(UUID id);
    void update(ScheduleUpdateInput updateInput);
    void delete(ScheduleDeleteInput deleteInput);
}
