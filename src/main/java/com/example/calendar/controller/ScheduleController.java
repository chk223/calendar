package com.example.calendar.controller;

import com.example.calendar.dto.ScheduleDeleteInput;
import com.example.calendar.dto.ScheduleDisplay;
import com.example.calendar.dto.ScheduleInput;
import com.example.calendar.dto.ScheduleUpdateInput;
import com.example.calendar.service.CalendarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/schedule")
@Slf4j
public class ScheduleController {
    private final CalendarService calendarService;

    @PostMapping("/add")
    public void addSchedule(@RequestBody ScheduleInput scheduleInput) {
        log.info("input = {} " , scheduleInput);
        calendarService.createSchedule(scheduleInput);
    }

    @GetMapping("/findAll")
    public List<ScheduleDisplay> getAllSchedule() {
        return calendarService.findAllScheduleBySort();
    }

    @GetMapping("/findSchedule")
    public ScheduleDisplay getSchedule(UUID id) {
        return calendarService.findScheduleById(id);
    }

    @PutMapping("/updateSchedule")
    public void updateSchedule(@RequestBody ScheduleUpdateInput updateInput) {
        calendarService.updateSchedule(updateInput);
    }
    @DeleteMapping("/deleteSchedule")
    public void deleteSchedule(@RequestBody ScheduleDeleteInput deleteInput) {
        calendarService.deleteSchedule(deleteInput);
    }
}
