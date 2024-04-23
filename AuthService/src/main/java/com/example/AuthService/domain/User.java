package com.example.AuthService.domain;

import com.example.AuthService.domain.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    private Long id;
    @Column(name = "username")
    private String userName;
    @Column(name = "password")
    private String password;
    @Column(name = "email")
    private String email;
    @Column(name = "status")
    private String status;
    @CreationTimestamp
    @Column(name = "create_date")
    private Date createDate;
    @UpdateTimestamp
    @Column(name = "update_date")
    private Date updateDate;
    public User(UserDTO dto){
        this.id=dto.getId();
        this.userName=dto.getUserName();
        this.password=dto.getPassword();
        this.email=dto.getEmail();
        this.status=dto.getStatus();
    }
}
