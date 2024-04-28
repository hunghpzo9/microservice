package com.example.AuthService.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GlobalException  extends RuntimeException{
    private HttpStatus httpStatus;
    private  String errorCode;
    private  String message;
}
