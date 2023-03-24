package com.example.point.service.domain.dto.outDto;

import com.example.point.service.domain.UserAccount;
import com.example.point.service.domain.dto.BaseOutDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAccountOutDTO extends BaseOutDTO {
    List<UserAccount> dtoList;
}
