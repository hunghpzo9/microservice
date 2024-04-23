package com.example.AuthService.repository;

import com.example.AuthService.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User,Long>,UserCustomRepository{

}
