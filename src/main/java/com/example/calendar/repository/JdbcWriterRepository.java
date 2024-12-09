package com.example.calendar.repository;

import com.example.calendar.domain.Writer;
import com.example.calendar.dto.WriterDeleteInput;
import com.example.calendar.dto.WriterUpdateInput;
import com.example.calendar.exception.ApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Repository
@RequiredArgsConstructor
@Primary
public class JdbcWriterRepository implements WriterRepository{
    private final JdbcTemplate jdbcTemplate;
    @Override
    public Writer join(Writer writer) {
        String sql = "insert into writer (id,name,email,joinedAt,updatedAt) values (?,?,?,?,?)";
        jdbcTemplate.update(sql,
                writer.getId().toString(),
                writer.getName(),
                writer.getEmail(),
                writer.getJoinedAt(),
                writer.getUpdatedAt());
        log.info("writer 가입 완료! id={}", writer.getId());
        return writer;
    }

    @Override
    public Optional<Writer> find(UUID id) {
        String sql = "select * from writer where id = ?";
        try {
            Writer writer = jdbcTemplate.queryForObject(sql, new Object[]{id.toString()},
                    (rs, rowNum) -> new Writer(
                            UUID.fromString(rs.getString("id")),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getTimestamp("joinedAt").toInstant().atZone(ZoneId.of("Asia/Seoul")),
                            rs.getTimestamp("updatedAt").toInstant().atZone(ZoneId.of("Asia/Seoul"))
                    ));
            return Optional.ofNullable(writer);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Writer> findAll() {
        String sql = "select * from writer";
        return jdbcTemplate.query(sql,
                (rs, rowNum) -> new Writer(
                        UUID.fromString(rs.getString("id")),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getTimestamp("joinedAt").toInstant().atZone(ZoneId.of("Asia/Seoul")),
                        rs.getTimestamp("updatedAt").toInstant().atZone(ZoneId.of("Asia/Seoul"))
                ));
    }

    @Override
    public void update(WriterUpdateInput updateInput) {
        String sql = "update writer set name = ?, email = ?, updatedAt = ? where id = ?";
        jdbcTemplate.update(sql,
                updateInput.getName(),
                updateInput.getEmail(),
                ZonedDateTime.now(),
                updateInput.getId().toString());
        log.info("writer 업데이트 완료! id={}", updateInput.getId());
    }

    @Override
    public void delete(WriterDeleteInput deleteInput) {
        String sql = "delete from writer where id = ?";
        jdbcTemplate.update(sql, deleteInput.getId().toString());
        log.info("writer 삭제 완료! id={}", deleteInput.getId());
    }

    @Override
    public void clearStorage() {
        String sql = "delete from writer";
        jdbcTemplate.update(sql);
        log.info("Writer 테이블 비우기 완료!");
    }
}
