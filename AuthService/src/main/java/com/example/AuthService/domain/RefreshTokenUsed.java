package com.example.AuthService.domain;

import com.example.AuthService.domain.dto.RefreshTokenUsedDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "refresh_token_used")
public class RefreshTokenUsed {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "token")
    private String token;
    @Column(name = "status")
    private String status;
    @CreationTimestamp
    @Column(name = "create_date")
    private Date createDate;
    @UpdateTimestamp
    @Column(name = "update_date")
    private Date updateDate;
    public RefreshTokenUsed(RefreshTokenUsedDTO dto){
        this.userId=dto.getUserId();
        this.token=dto.getToken();
        this.status=dto.getStatus();
        this.createDate=dto.getCreateDate();
    }
}
