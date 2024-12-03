package com.example.calendar.service;

import com.example.calendar.domain.Schedule;
import com.example.calendar.domain.Writer;
import com.example.calendar.dto.ScheduleDeleteInput;
import com.example.calendar.dto.ScheduleDisplay;
import com.example.calendar.dto.ScheduleInput;
import com.example.calendar.dto.ScheduleUpdateInput;
import com.example.calendar.repository.ScheduleRepository;
import com.example.calendar.repository.WriterRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CalendarServiceImplTest {
    @Autowired
    ScheduleRepository scheduleRepository;
    @Autowired
    WriterRepository writerRepository;

    @AfterEach
    void afterEach() {
        scheduleRepository.clearStorage();
        writerRepository.clearStorage();
    }

    @Test
    void createSchedule() {
        //given
        Writer writer = writerRepository.join(new Writer("writer", "writer@naver.com"));
        ScheduleInput scheduleInput = new ScheduleInput("my-todo",writer.getId(),"my-pw");
        //when
        Schedule schedule = new Schedule(scheduleInput.getTodo(),scheduleInput.getWriterId(),
                scheduleInput.getPassword());
        scheduleRepository.create(schedule);
        //then
        assertThat(scheduleInput.getWriterId()).isEqualByComparingTo(writer.getId());
    }

    @Test
    void findAllScheduleBySort() {
        //given
        Writer writer = writerRepository.join(new Writer("writer", "writer@naver.com"));
        ScheduleInput scheduleInput = new ScheduleInput("my-todo",writer.getId(),"my-pw");
        Schedule schedule2 = new Schedule(scheduleInput.getTodo(),scheduleInput.getWriterId(),
                scheduleInput.getPassword());
        scheduleRepository.create(schedule2);
        ScheduleInput scheduleInput1 = new ScheduleInput("my-todo",writer.getId(),"my-pw");
        Schedule schedule1 = new Schedule(scheduleInput1.getTodo(),scheduleInput1.getWriterId(),
                scheduleInput1.getPassword());
        scheduleRepository.create(schedule1);
        //when
        List<Schedule> scheduleList = scheduleRepository.findAll();
        List<ScheduleDisplay> scheduleDisplays = scheduleList.stream()
                .map(schedule -> new ScheduleDisplay(schedule.getTodo(), writerRepository.find(schedule.getWriterId()).getName(), schedule.getCreatedAt(), schedule.getUpdatedAt()))  // 변환
                .sorted(Comparator.comparing(ScheduleDisplay::getEditedAt).reversed())  // 정렬
                .collect(Collectors.toList());
        //then
        assertThat(scheduleDisplays.size()).isEqualTo(2);
    }

    @Test
    void findScheduleById() {
        //given
        Writer writer = writerRepository.join(new Writer("writer", "writer@naver.com"));
        ScheduleInput scheduleInput = new ScheduleInput("my-todo",writer.getId(),"my-pw");
        Schedule schedule2 = new Schedule(scheduleInput.getTodo(),scheduleInput.getWriterId(),
                scheduleInput.getPassword());
        scheduleRepository.create(schedule2);
        ScheduleInput scheduleInput1 = new ScheduleInput("my-todo",writer.getId(),"my-pw");
        Schedule schedule1 = new Schedule(scheduleInput1.getTodo(),scheduleInput1.getWriterId(),
                scheduleInput1.getPassword());
        scheduleRepository.create(schedule1);
        //when
        UUID id = schedule2.getId();
        Schedule schedule = scheduleRepository.find(id);
        //then
        assertThat(schedule).isEqualTo(schedule2);
    }

    @Test
    void updateSchedule() {
        //given
        Writer writer = writerRepository.join(new Writer("writer", "writer@naver.com"));
        ScheduleInput scheduleInput = new ScheduleInput("my-todo",writer.getId(),"my-pw");
        Schedule schedule1 = new Schedule(scheduleInput.getTodo(),scheduleInput.getWriterId(),
                scheduleInput.getPassword());
        scheduleRepository.create(schedule1);
        ScheduleUpdateInput updateInput = new ScheduleUpdateInput(schedule1.getId(), "my-modified-todo", "my-pw");
        //when
        Schedule schedule = scheduleRepository.find(updateInput.getId());
        if(schedule.getPassword().equals(updateInput.getPassword())) {
            scheduleRepository.update(updateInput);
        }
        //then
        Schedule schedule2 = scheduleRepository.find(schedule1.getId());
        assertThat(schedule2.getTodo()).isEqualTo(updateInput.getTodo());
    }

    @Test
    void deleteSchedule() {
        //given
        Writer writer = writerRepository.join(new Writer("writer", "writer@naver.com"));
        ScheduleInput scheduleInput = new ScheduleInput("my-todo",writer.getId(),"my-pw");
        Schedule schedule2 = new Schedule(scheduleInput.getTodo(),scheduleInput.getWriterId(),
                scheduleInput.getPassword());
        scheduleRepository.create(schedule2);
        ScheduleInput scheduleInput1 = new ScheduleInput("my-todo",writer.getId(),"my-pw");
        Schedule schedule1 = new Schedule(scheduleInput1.getTodo(),scheduleInput1.getWriterId(),
                scheduleInput1.getPassword());
        scheduleRepository.create(schedule1);
        ScheduleDeleteInput deleteInput = new ScheduleDeleteInput(schedule2.getId(),schedule2.getPassword());
        //when
        Schedule schedule = scheduleRepository.find(deleteInput.getId());
        if(schedule.getPassword().equals(deleteInput.getPassword())) {
            scheduleRepository.delete(deleteInput);
        }
        scheduleRepository.delete(deleteInput);
        //then
        assertThat(scheduleRepository.findAll().size()).isEqualTo(1);
    }
}