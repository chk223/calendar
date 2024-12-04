package com.example.calendar.service;

import com.example.calendar.dto.WriterDeleteInput;
import com.example.calendar.dto.WriterDisplay;
import com.example.calendar.dto.WriterInput;
import com.example.calendar.dto.WriterUpdateInput;

import java.util.List;
import java.util.UUID;

public interface WriterService {
    /**
     * 작성자 정보 등록(회원등록과 유사한 기능)
     * @param input 작성자 정보(name, email)
     */
    void signUpWriter(WriterInput input);

    /**
     * 작성자 정보 수정
     * @param input 수정할 정보 데이터(id, name, email) -> id를 통해 작성자 색출 후 정보 변경
     */
    void editWriterInfo(WriterUpdateInput input);

    /**
     * 모든 작성자 리스트 반환
     * @return WriterDisplay(name,email,createdAt,updatedAt)를 리스트에 담아서 반환
     */
    List<WriterDisplay> writerList();

    /**
     * 특정 작성자의 정보 반환
     * @param id 작성자를 식별하기 위한 고유 식별자 id
     * @return WriterDisplay(name,email,createdAt,updatedAt)에 작성자의 정보를 담아서 반환
     */
    WriterDisplay writerInfo(UUID id);

    /**
     * 작성자 정보 삭제(회원탈퇴)
     * @param deleteInput 탈퇴할 회원의 정보를 담은 객체
     */
    void withdraw(WriterDeleteInput deleteInput);
}
