package com.example.calendar.repository;

import com.example.calendar.domain.Schedule;
import com.example.calendar.dto.ScheduleDeleteInput;
import com.example.calendar.dto.ScheduleUpdateInput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Slf4j
public class MemoryScheduleRepository implements ScheduleRepository {
    private final Map<UUID, Schedule> scheduleStorage = new ConcurrentHashMap<>();

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

    @Override
    public void update(ScheduleUpdateInput updateInput) {
        Schedule targetSchedule = scheduleStorage.get(updateInput.getId());
        targetSchedule.setTodo(updateInput.getTodo());
        targetSchedule.setUpdatedAt(LocalDateTime.now());
    }

    @Override
    public void delete(ScheduleDeleteInput deleteInput) {
        scheduleStorage.remove(deleteInput.getId());
    }

    @Override
    public void clearStorage() {
        scheduleStorage.clear();
    }
}
