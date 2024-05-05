package com.example.AuthService.domain.dto.outDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class BaseOutDTO {
    private HttpStatus httpStatus;
    private String code;
    private String message;
    private Date timestamp;


    public void setCodeAndMessage(String code, String message) {
        this.code = code;
        this.message = message;
        this.timestamp = new Date();
    }

    public void setResponseBadRequest(String code, String message) {
        setCodeAndMessage(code, message);
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }

    public void setResponseSuccess(String code, String message) {
        setCodeAndMessage(code, message);
        this.httpStatus = HttpStatus.OK;
    }

    public void setResponseCreated(String code, String message) {
        setCodeAndMessage(code, message);
        this.httpStatus = HttpStatus.CREATED;
    }

    public void setResponseInternalServerError(String code, String message) {
        setCodeAndMessage(code, message);
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }
    public void setResponseAuthenticateFail(String code, String message) {
        setCodeAndMessage(code, message);
        this.httpStatus = HttpStatus.FORBIDDEN;
    }


}
