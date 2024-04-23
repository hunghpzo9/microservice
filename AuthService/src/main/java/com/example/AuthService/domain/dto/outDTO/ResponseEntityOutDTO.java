package com.example.AuthService.domain.dto.outDTO;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseEntityOutDTO extends ResponseEntity {
    public ResponseEntityOutDTO(BaseOutDTO outDTO) {
        super(outDTO, outDTO.getHttpStatus());
    }
}
