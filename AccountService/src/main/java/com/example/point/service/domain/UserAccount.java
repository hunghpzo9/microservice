package com.example.point.service.domain;

import com.example.point.service.domain.dto.UserAccountDTO;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "user_account")
public class UserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "create_date")
    @CreationTimestamp
    private Date createDate;

    @Column(name = "update_date")
    @UpdateTimestamp
    private Date updateDate;

    public UserAccount(UserAccountDTO dto){
        this.id = dto.getId();
        this.name=dto.getName();
        this.createDate=dto.getCreateDate();
        this.updateDate=dto.getUpdateDate();
    }
}
