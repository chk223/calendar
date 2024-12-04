package com.example.calendar.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class Page<T> {
    /**
     * 데이터
     */
    private List<T> content;
    /**
     * 총 데이터 개수
     */
    private int totalDataCount;
    /**
     * 전체 페이지 수
     */
    private int totalPages;
    /**
     * 현재 페이지
     */
    private int nowPage;

    public Page(List<T> content, int totalDataCount, int page, int size) {
        this.content = content;
        this.totalDataCount = totalDataCount;
        this.totalPages = (int) Math.ceil((double) totalDataCount /size);
        this.nowPage = page;
    }
}
