package com.example.blog.MyException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Emma
 * @create 2020 - 05 - 21 - 17:14
 */
@ResponseStatus(HttpStatus.NOT_FOUND)  //指定返回状态码
public class NotFoundException  extends RuntimeException{

    public NotFoundException() {
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
