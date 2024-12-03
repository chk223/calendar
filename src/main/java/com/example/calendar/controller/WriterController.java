package com.example.calendar.controller;

import com.example.calendar.dto.WriterDeleteInput;
import com.example.calendar.dto.WriterDisplay;
import com.example.calendar.dto.WriterInput;
import com.example.calendar.dto.WriterUpdateInput;
import com.example.calendar.service.WriterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/writer")
public class WriterController {
    private final WriterService writerService;
    @PostMapping("/sign-up")
    public void signUp(@RequestBody WriterInput writerInput) {
        writerService.signUpWriter(writerInput);
    }
    @GetMapping("/all-writer")
    public List<WriterDisplay> getAllWriter () {
        return writerService.writerList();
    }
    @GetMapping
    public WriterDisplay getWriterInfo(UUID id) {
        return writerService.writerInfo(id);
    }
    @PutMapping
    public void editInfo(@RequestBody WriterUpdateInput updateInput) {
        writerService.editWriterInfo(updateInput);
    }
    @DeleteMapping
    public void withdraw(@RequestBody WriterDeleteInput deleteInput) {
        writerService.withdraw(deleteInput);
    }

}
