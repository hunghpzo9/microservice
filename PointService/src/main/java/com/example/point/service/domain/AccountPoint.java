package com.example.point.service.domain;

import com.example.point.service.domain.dto.AccountPointDTO;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;

@Table(name = "account_point", indexes = {
        @Index(name = "account_point_id", columnList = "account_point_id")
})
@Data
@NoArgsConstructor
@Entity
public class AccountPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_point_id", nullable = false)
    private Long accountPointId;

    @Column(name = "point_value")
    private Integer pointValue;

    @Column(name = "create_date")
    @CreationTimestamp
    private Date createDate;

    @Column(name = "update_date")
    @UpdateTimestamp
    private Date updateDate;

    @Column(name = "point_expire_date")
    private Date pointExpireDate;

    @Column(name = "user_account_id")
    private Long userAccountId;

    public AccountPoint(AccountPointDTO dto) {
        this.accountPointId = dto.getAccountPointId();
        this.pointValue = dto.getPointValue();
        this.createDate = dto.getCreateDate();
        this.updateDate = dto.getUpdateDate();
        this.pointExpireDate = dto.getPointExpireDate();
        this.userAccountId = dto.getUserAccountId();
    }
}