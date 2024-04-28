package com.example.AuthService.exception;

import com.example.AuthService.domain.dto.outDTO.BaseOutDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.UUID;

@Slf4j
public class ErrorException extends RuntimeException{
    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<?> handleApplicationException(
            final GlobalException exception, final HttpServletRequest request
    ) {
        var guid = UUID.randomUUID().toString();
        log.error(
                String.format("Error GUID=%s; error message: %s", guid, exception.getMessage()),
                exception
        );
        var response = new BaseOutDTO(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "004",
                "Error server");
        return new ResponseEntity<>(response, exception.getHttpStatus());
    }
}
