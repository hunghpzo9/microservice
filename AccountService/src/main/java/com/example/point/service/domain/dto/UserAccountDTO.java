package com.example.point.service.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAccountDTO implements Serializable  {
    private Long id;
    private String name;
    private Date createDate;
    private Date updateDate;
}
