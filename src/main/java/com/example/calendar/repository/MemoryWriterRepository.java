package com.example.calendar.repository;

import com.example.calendar.domain.Writer;
import com.example.calendar.dto.WriterDeleteInput;
import com.example.calendar.dto.WriterUpdateInput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
@Slf4j
@Repository
public class MemoryWriterRepository implements WriterRepository{
    private final Map<UUID, Writer> writerStorage = new ConcurrentHashMap<>();
    @Override
    public Writer join(Writer writer) {
        log.info("writer 가입 완료! id={}", writer.getId());
        writerStorage.put(writer.getId(), writer);
        return writer;
    }
    @Override
    public Optional<Writer> find(UUID id) {
        return Optional.of(writerStorage.get(id));
    }

    @Override
    public List<Writer> findAll() {
        return writerStorage.values().stream().toList();
    }

    @Override
    public void update(WriterUpdateInput updateInput) {
        Writer writer = writerStorage.get(updateInput.getId());
        writer.setName(updateInput.getName());
        writer.setEmail(updateInput.getEmail());
        writer.setUpdatedAt(LocalDateTime.now());
        writerStorage.put(writer.getId(), writer);
    }

    @Override
    public void delete(WriterDeleteInput deleteInput) {
        writerStorage.remove(deleteInput.getId());
    }

    @Override
    public void clearStorage() {
        writerStorage.clear();
    }
}
