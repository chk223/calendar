package com.example.calendar.service;

import com.example.calendar.domain.Writer;
import com.example.calendar.dto.WriterDeleteInput;
import com.example.calendar.dto.WriterDisplay;
import com.example.calendar.dto.WriterInput;
import com.example.calendar.dto.WriterUpdateInput;
import com.example.calendar.repository.WriterRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class WriterServiceImplTest {
    @Autowired
    WriterRepository writerRepository;

    @AfterEach
    void afterEach() {
        writerRepository.clearStorage();
    }

    @Test
    void signUpWriter() {
        //given
        WriterInput input = new WriterInput("writer", "email@email.com");
        Writer writer = new Writer(input.getName(),input.getEmail());
        //when
        writerRepository.join(writer);
        //then
        assertThat(writerRepository.findAll().size()).isEqualTo(1);
    }

    @Test
    void editWriterInfo() {
        //given
        WriterInput editInput = new WriterInput("writer", "email@email.com");
        Writer writer = new Writer(editInput.getName(),editInput.getEmail());
        writerRepository.join(writer);
        WriterUpdateInput updateInput= new WriterUpdateInput(writer.getId(),"name","email");
        //when
        Writer targetWriter = writerRepository.find(updateInput.getId()).orElseThrow();
        writer.setName(updateInput.getName());
        writer.setEmail(updateInput.getEmail());
        writer.setUpdatedAt(LocalDateTime.now());
        writerRepository.update(updateInput);
        Writer writer3 = writerRepository.find(updateInput.getId()).orElseThrow();
        //then
        assertThat(writer3.getEmail()).isEqualTo("email");
        assertThat(writer3.getName()).isEqualTo("name");
    }

    @Test
    void writerList() {
        //given
        WriterInput editInput1 = new WriterInput("writer", "email@email.com");
        Writer writer1 = new Writer(editInput1.getName(),editInput1.getEmail());
        writerRepository.join(writer1);
        WriterInput editInput2 = new WriterInput("writer", "email@email.com");
        Writer writer2 = new Writer(editInput2.getName(),editInput2.getEmail());
        writerRepository.join(writer2);
        //when
        List<Writer> writers = writerRepository.findAll();
        List<WriterDisplay> sortedWriters = writers.stream()
                .map(writer -> new WriterDisplay(writer.getName(), writer.getEmail(), writer.getJoinedAt(), writer.getUpdatedAt()))  // 변환
                .sorted(Comparator.comparing(WriterDisplay::getUpdatedAt).reversed())  // 정렬
                .toList();
        //then
        assertThat(sortedWriters.size()).isEqualTo(2);
    }

    @Test
    void writerInfo() {
        //given
        WriterInput input = new WriterInput("writer", "email@email.com");
        Writer writer = new Writer(input.getName(),input.getEmail());
        writerRepository.join(writer);
        //when
        Writer foundWriter = writerRepository.find(writer.getId()).orElseThrow();
        WriterDisplay writerDisplay = new WriterDisplay(foundWriter.getName(), foundWriter.getEmail(), foundWriter.getJoinedAt(), foundWriter.getUpdatedAt());
        //then
        assertThat(writerDisplay.getEmail()).isEqualTo(writer.getEmail());
        assertThat(writerDisplay.getName()).isEqualTo(writer.getName());
    }

    @Test
    void withdraw() {
        //given
        WriterInput input1 = new WriterInput("writer", "email@email.com");
        Writer writer1 = new Writer(input1.getName(),input1.getEmail());
        writerRepository.join(writer1);
        WriterInput input2 = new WriterInput("writer", "email@email.com");
        Writer writer2 = new Writer(input2.getName(),input2.getEmail());
        writerRepository.join(writer2);

        WriterDeleteInput deleteInput = new WriterDeleteInput(writer1.getId());
        //when
        writerRepository.delete(deleteInput);
        //then
        assertThat(writerRepository.findAll().size()).isEqualTo(1);
    }
}