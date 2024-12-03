package com.example.calendar.repository;

import com.example.calendar.domain.Writer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
@Slf4j
@Repository
public class MemoryWriterRepository implements WriterRepository{
    private final Map<UUID, Writer> writerStorage = new ConcurrentHashMap<>();
    @Override
    public void join(Writer writer) {
        log.info("writer 가입 완료! id={}", writer.getId());
        writerStorage.put(writer.getId(), writer);
    }
    @Override
    public Writer find(UUID id) {
        return writerStorage.get(id);
    }

    @Override
    public List<Writer> findAll() {
        return writerStorage.values().stream().toList();
    }

    @Override
    public void update(Writer writer) {
        writerStorage.put(writer.getId(), writer);
    }

    @Override
    public void delete(UUID id) {
        writerStorage.remove(id);
    }
}
