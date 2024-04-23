package com.example.AuthService.domain;

import com.example.AuthService.domain.dto.KeyTokenDTO;
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
@Table(name = "key_token")
public class KeyToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "public_key")
    private String publicKey;
    @Column(name = "refresh_token")
    private String refreshToken;
    @Column(name = "status")
    private String status;
    @CreationTimestamp
    @Column(name = "create_date")
    private Date createDate;
    @UpdateTimestamp
    @Column(name = "update_date")
    private Date updateDate;
    public KeyToken (KeyTokenDTO dto){
        this.id=dto.getId();
        this.userId=dto.getUserId();
        this.publicKey=dto.getPublicKey();
        this.refreshToken=dto.getRefreshToken();
        this.status=dto.getStatus();
    }
}
