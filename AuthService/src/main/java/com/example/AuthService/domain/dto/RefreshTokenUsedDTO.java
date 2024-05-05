package com.example.AuthService.domain.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RefreshTokenUsedDTO {
    private Long userId;
    private String token;
    private String status;
    private Date createDate;
    private Date updateDate;
}
