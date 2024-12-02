package com.example.calendar.repository;

import com.example.calendar.domain.Schedule;

import java.util.List;
import java.util.UUID;

public interface CalendarRepository {
    void create(Schedule schedule);
    List<Schedule> findAll();
    Schedule find(UUID id);
}
