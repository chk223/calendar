package com.example.calendar.repository;

import com.example.calendar.domain.Schedule;
import com.example.calendar.dto.ScheduleDeleteInput;
import com.example.calendar.dto.ScheduleUpdateInput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Slf4j
@Repository
@RequiredArgsConstructor
@Primary
public class JdbcScheduleRepository implements ScheduleRepository{
    private final JdbcTemplate jdbcTemplate;
    @Override
    public Schedule create(Schedule schedule) {
        String sql = "insert into schedule (id,todo,writerId,password,createdAt,updatedAt) values (?,?,?,?,?,?)";
        jdbcTemplate.update(sql,
                schedule.getId().toString(),
                schedule.getTodo(),
                schedule.getWriterId().toString(),
                schedule.getPassword(),
                schedule.getCreatedAt(),
                schedule.getUpdatedAt());
        log.info("schedule 등록 완료! id= {}" , schedule.getId());
        return null;
    }

    @Override
    public List<Schedule> findAll(int startPoint, int size) {//
        String sql = "select * from schedule order by updatedAt desc limit ? offset ?";
        return jdbcTemplate.query(sql, new Object[]{size, startPoint},
                (rs, rowNum) -> new Schedule(
                        UUID.fromString(rs.getString("id")),
                        rs.getString("todo"),
                        UUID.fromString(rs.getString("writerId")),
                        rs.getString("password"),
                        rs.getTimestamp("createdAt").toInstant().atZone(ZoneId.of("Asia/Seoul")),
                        rs.getTimestamp("updatedAt").toInstant().atZone(ZoneId.of("Asia/Seoul"))
                ));
    }

    @Override
    public int countAll() {
        String sql = "select count(*) from schedule";
        Integer result = jdbcTemplate.queryForObject(sql, Integer.class);
        return result == null? 0 : result;//null 값이면 0 반환
    }

    @Override
    public Optional<Schedule> find(UUID id) {
        String sql = "select * from schedule where id=?";
        try {
            Schedule schedule = jdbcTemplate.queryForObject(sql, new Object[]{id.toString()},
                    (rs, rowNum) -> new Schedule(
                            UUID.fromString(rs.getString("id")),
                            rs.getString("todo"),
                            UUID.fromString(rs.getString("writerId")),
                            rs.getString("password"),
                            rs.getTimestamp("createdAt").toInstant().atZone(ZoneId.of("Asia/Seoul")),
                            rs.getTimestamp("updatedAt").toInstant().atZone(ZoneId.of("Asia/Seoul"))
                    ));
            return Optional.ofNullable(schedule);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Schedule> findByWriterId(UUID id) {
        String sql = "select * from schedule where writerId = ? order by updatedAt desc";
        try {
            return jdbcTemplate.query(sql, new Object[]{id.toString()},
                    (rs, rowNum) -> new Schedule(
                            UUID.fromString(rs.getString("id")),
                            rs.getString("todo"),
                            UUID.fromString(rs.getString("writerId")),
                            rs.getString("password"),
                            rs.getTimestamp("createdAt").toInstant().atZone(ZoneId.of("Asia/Seoul")),
                            rs.getTimestamp("updatedAt").toInstant().atZone(ZoneId.of("Asia/Seoul"))
                    ));
        } catch (EmptyResultDataAccessException e) {
            return List.of();
        }
    }

    @Override
    public void update(ScheduleUpdateInput updateInput) {
        String sql = "update schedule set todo = ?, updatedAt = ? where id = ?";
        jdbcTemplate.update(sql,
                updateInput.getTodo(),
                ZonedDateTime.now(ZoneId.of("Asia/Seoul")),
                updateInput.getId().toString());
        log.info("schedule 업데이트 완료! id={}", updateInput.getId());
    }

    @Override
    public void delete(ScheduleDeleteInput deleteInput) {
        String sql = "delete from schedule where id = ?";
        jdbcTemplate.update(sql, deleteInput.getId().toString());
        log.info("schedule 삭제 완료! id={}", deleteInput.getId());
    }

    @Override
    public void clearStorage() {
        String sql = "delete from schedule";
        jdbcTemplate.update(sql);
        log.info("Schedule 테이블 비우기 완료!");
    }
}
