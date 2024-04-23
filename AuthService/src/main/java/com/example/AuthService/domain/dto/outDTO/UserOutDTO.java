package com.example.AuthService.domain.dto.outDTO;

import com.example.AuthService.domain.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserOutDTO extends BaseOutDTO implements Serializable {
    List<UserDTO> userDTOList;

}
