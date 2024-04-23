package com.example.AuthService.domain.dto.inDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInDTO {
    private String userName;
    private String password;
    private String email;
    private Long status;
}
