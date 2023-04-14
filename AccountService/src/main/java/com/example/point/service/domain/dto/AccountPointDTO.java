package com.example.point.service.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountPointDTO implements Serializable {

    private Long accountPointId;

    private Integer pointValue;

    private Date createDate;

    private Date updateDate;

    private Date pointExpireDate;

    private Long userAccountId;
}
