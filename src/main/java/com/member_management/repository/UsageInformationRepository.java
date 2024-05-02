package com.member_management.repository;

import com.member_management.modules._UsageInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsageInformationRepository extends JpaRepository<_UsageInformation, Integer> {
    
}
