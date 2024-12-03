package com.example.calendar.repository;

import com.example.calendar.domain.Writer;

import java.util.List;
import java.util.UUID;

public interface WriterRepository {
    void join(Writer writer);
    Writer find(UUID id);
    List<Writer> findAll();
    void update(Writer writer);
    void delete(UUID id);
}
