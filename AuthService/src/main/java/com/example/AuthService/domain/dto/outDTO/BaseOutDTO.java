package com.example.AuthService.domain.dto.outDTO;

import com.example.AuthService.utils.Const;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseOutDTO {
    private HttpStatus httpStatus;
    private String code;
    private String message;
    public void setCode(String code) {
        this.code = code;
        switch(code){
            case Const.RESPONSE_CODE.SUCCESS:
                this.httpStatus=HttpStatus.OK;
                break;
            case Const.RESPONSE_CODE.CREATED:
                this.httpStatus=HttpStatus.CREATED;
                break;
            case Const.RESPONSE_CODE.DATA_INVALID:
            case Const.RESPONSE_CODE.EMAIL_INVALID:
            case Const.RESPONSE_CODE.PASSWORD_WEAK:
                this.httpStatus=HttpStatus.BAD_REQUEST;
                break;
            case Const.RESPONSE_CODE.EMAIL_EXISTED:
                this.httpStatus=HttpStatus.CONFLICT;
                break;
            case Const.RESPONSE_CODE.ERROR:
                this.httpStatus=HttpStatus.INTERNAL_SERVER_ERROR;
                break;
        }
    }

}
