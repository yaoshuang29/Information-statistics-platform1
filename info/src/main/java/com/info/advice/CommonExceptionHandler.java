package com.info.advice;


import com.info.view.ExceptionResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 通用异常处理类
 */

@ControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(InfoException.class)
    public ResponseEntity<ExceptionResult> handlerException(InfoException info){
        return ResponseEntity.status(info.getExceptionEnum().getStatus()).body(new ExceptionResult(info.getExceptionEnum()));
    }
}
