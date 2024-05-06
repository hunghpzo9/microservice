package com.example.AuthService.domain.dto.outDTO;

import com.example.AuthService.domain.dto.TokenDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenOutDTO  extends BaseOutDTO implements Serializable {
    TokenDTO tokenDTO;
}
