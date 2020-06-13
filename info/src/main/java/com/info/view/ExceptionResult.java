package com.info.view;

import com.info.enums.ExceptionEnum;
import lombok.Data;

@Data
public class ExceptionResult {
    private Integer status;
    private String msg;
    private Long timestamp;

    public ExceptionResult(ExceptionEnum em){
        this.status=em.getStatus();
        this.msg=em.getMsg();
        this.timestamp=System.currentTimeMillis();
    }
}
