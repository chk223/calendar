package com.example.calendar.repository;

import com.example.calendar.domain.Writer;
import com.example.calendar.dto.WriterDeleteInput;
import com.example.calendar.dto.WriterUpdateInput;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WriterRepository {
    /**
     * 작성자 등록
     * @param writer 작성자 객체
     * @return 등록한 작성자 객체(현재는 테스트를 위함)
     */
    Writer join(Writer writer);

    /**
     * 고유 식별자를 통해 작성자 조회
     * @param id 고유 식별자
     * @return 작성자 객체
     */
    Optional<Writer> find(UUID id);

    /**
     * 모든 작성자 조회
     * @return 작성자 리스트
     */
    List<Writer> findAll();

    /**
     * 작성자 정보 갱신
     * @param updateInput 갱신할 작성자 정보를 담고 있는 객체
     */
    void update(WriterUpdateInput updateInput);

    /**
     * 작성자 정보 삭제
     * @param deleteInput 식별 정보를 담고 있는 객체
     */
    void delete(WriterDeleteInput deleteInput);

    /**
     * 테스트 코드를 위한 메모리 저장소 비우는 기능
     */
    void clearStorage();
}
