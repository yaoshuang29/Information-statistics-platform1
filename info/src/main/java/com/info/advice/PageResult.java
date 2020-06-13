package com.info.advice;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PageResult<T> {

    public PageResult(){

    }

    private Long total;
    private Integer totalPage;
    private List<T> list;

}
