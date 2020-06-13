package com.info.advice;

import com.info.enums.ExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class InfoException  extends RuntimeException{
    private ExceptionEnum exceptionEnum;
}
