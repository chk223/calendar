package com.example.calendar.dto;

import lombok.Getter;

@Getter
public class PageRequest {
    /**
     * 현재 페이지
     */
    private int page;
    /**
     * 페이지 사이즈
     */
    private int size;
    /**
     * 페이지 시작 지점 -> sql 쿼리 offset 값
     */
    private int startPoint;

    public PageRequest(int page, int size) {
        this.page = page;
        this.size = size;
        this.startPoint = page * size;
    }
}
