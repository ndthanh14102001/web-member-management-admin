package com.member_management.repository;

import com.member_management.modules._Processing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessingRepository extends JpaRepository<_Processing, Integer> {
    
}
