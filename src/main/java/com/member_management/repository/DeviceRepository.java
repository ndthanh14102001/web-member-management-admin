package com.member_management.repository;

import com.member_management.modules._Device;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<_Device, Integer> {
    
}
