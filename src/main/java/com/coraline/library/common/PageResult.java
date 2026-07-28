package com.coraline.library.common;

import lombok.Data;

import java.util.List;

@Data
public class PageResult<T> {

    private List<T> records;

    private Long total;


    public PageResult(List<T> records, Long total) {
        this.records = records;
        this.total = total;
    }
}
