package com.jimas.web.support;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author liuqj
 */
@ControllerAdvice
@Slf4j
public class GlobalException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = Throwable.class)
    public ResponseEntity<Object> exceptionHandler(Throwable e) {
        log.error("全局异常：", e);
        return ResponseEntity.ok("未知异常");
    }
}
