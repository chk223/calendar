package com.example.calendar.service;

import com.example.calendar.dto.WriterDeleteInput;
import com.example.calendar.dto.WriterDisplay;
import com.example.calendar.dto.WriterInput;
import com.example.calendar.dto.WriterUpdateInput;

import java.util.List;
import java.util.UUID;

public interface WriterService {
    void signUpWriter(WriterInput input);
    void editWriterInfo(WriterUpdateInput input);
    List<WriterDisplay> writerList();
    WriterDisplay writerInfo(UUID id);
    void withdraw(WriterDeleteInput deleteInput);
}
