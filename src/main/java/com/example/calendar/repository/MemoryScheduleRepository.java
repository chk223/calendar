package com.example.calendar.repository;

import com.example.calendar.domain.Schedule;
import com.example.calendar.dto.ScheduleDeleteInput;
import com.example.calendar.dto.ScheduleUpdateInput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Slf4j
public class MemoryScheduleRepository implements ScheduleRepository {
    private final Map<UUID, Schedule> scheduleStorage = new ConcurrentHashMap<>();

    @Override
    public void create(Schedule schedule) {
        scheduleStorage.put(schedule.getId(), schedule);
        log.info("schedule id= {}" , schedule.getId());
    }

    @Override
    public List<Schedule> findAll(int startPoint, int size) {
        if(startPoint >= scheduleStorage.size()) return new ArrayList<>();
        return scheduleStorage.values().stream()
                .sorted(Comparator.comparing(Schedule::getUpdatedAt))  // 최신 업데이트 날짜를 기준으로 정렬해서 반환해줌
                .skip(startPoint)
                .limit(size)
                .toList();
    }

    @Override
    public int countAll() {
        return scheduleStorage.size();
    }

    @Override
    public Optional<Schedule> find(UUID id) {
        return Optional.of(scheduleStorage.get(id));
    }

    @Override
    public List<Schedule> findByWriterId(UUID id) {
        return scheduleStorage.values().stream().filter(schedule -> schedule.getWriterId().equals(id))
                .sorted(Comparator.comparing(Schedule::getUpdatedAt))
                .toList();
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
