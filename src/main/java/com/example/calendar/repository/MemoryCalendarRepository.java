package com.example.calendar.repository;

import com.example.calendar.domain.Schedule;
import com.example.calendar.dto.ScheduleInput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Slf4j
public class MemoryCalendarRepository implements CalendarRepository {
    Map<UUID, Schedule> scheduleStorage = new ConcurrentHashMap<>();

    @Override
    public void create(Schedule schedule) {
        scheduleStorage.put(schedule.getId(), schedule);
        log.info("id= {}" , schedule.getId());
    }

    @Override
    public List<Schedule> findAll() {
        return scheduleStorage.values().stream().toList();
    }

    @Override
    public Schedule find(UUID id) {
        return scheduleStorage.get(id);
    }
}
