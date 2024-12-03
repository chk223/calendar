package com.example.calendar.service;

import com.example.calendar.domain.Writer;
import com.example.calendar.dto.*;
import com.example.calendar.repository.WriterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class WriterServiceImpl implements WriterService{
    private final WriterRepository writerRepository;
    @Override
    public void signUpWriter(WriterInput input) {
        Writer writer = new Writer(input.getName(),input.getEmail());
        writerRepository.join(writer);
    }

    @Override
    public void editWriterInfo(WriterUpdateInput input) {
        Writer writer = writerRepository.find(input.getId());
        writer.setName(input.getName());
        writer.setEmail(input.getEmail());
        writer.setUpdatedAt(LocalDateTime.now());
        writerRepository.update(writer);
    }

    @Override
    public List<WriterDisplay> writerList() {
        List<Writer> writers = writerRepository.findAll();
        if(writers.isEmpty()) {
            log.warn("등록된 작성자가 없습니다!");
            return new ArrayList<>();
        }
        else return writers.stream()
                .map(writer -> new WriterDisplay(writer.getName(),writer.getEmail(),writer.getJoinedAt(),writer.getUpdatedAt()))  // 변환
                .sorted(Comparator.comparing(WriterDisplay::getUpdatedAt).reversed())  // 정렬
                .collect(Collectors.toList());
    }

    @Override
    public WriterDisplay writerInfo(UUID id) {
        Writer writer = writerRepository.find(id);
        return new WriterDisplay(writer.getName(), writer.getEmail(), writer.getJoinedAt(), writer.getUpdatedAt());
    }

    @Override
    public void withdraw(WriterDeleteInput deleteInput) {
        writerRepository.delete(deleteInput.getId());
    }
}
