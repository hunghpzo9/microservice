package com.example.point.service.repository;

import com.example.point.service.domain.AccountPoint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountPointRepository extends JpaRepository<AccountPoint,Long> {
}
