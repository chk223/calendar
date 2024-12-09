package com.example.calendar.service;

import com.example.calendar.domain.Writer;
import com.example.calendar.dto.WriterDeleteInput;
import com.example.calendar.dto.WriterDisplay;
import com.example.calendar.dto.WriterInput;
import com.example.calendar.dto.WriterUpdateInput;
import com.example.calendar.exception.ApiException;
import com.example.calendar.exception.ErrorMessage;
import com.example.calendar.repository.WriterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
    public void editWriterInfo(WriterUpdateInput updateInput) {
        writerRepository.update(updateInput);
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
        Writer writer = getWriterById(id);
        return new WriterDisplay(writer.getName(), writer.getEmail(), writer.getJoinedAt(), writer.getUpdatedAt());
    }

    @Override
    public void withdraw(WriterDeleteInput deleteInput) {
        writerRepository.delete(deleteInput);
    }

    /**
     * id를 통해 작성자 객체 찾기
     * @param id 찾고자 하는 작성자 id
     * @return 찾은 작성자 객체
     */
    private Writer getWriterById(UUID id) {
        return writerRepository.find(id).orElseThrow(() -> {
            log.warn("해당 id를 가진 작성자가 없습니다. 입력한 id={}", id);
            ErrorMessage errorMessage = ErrorMessage.WRITER_NOT_FOUND;
            return new ApiException(errorMessage.getMessage(), errorMessage.getStatus());
        });
    }
}
