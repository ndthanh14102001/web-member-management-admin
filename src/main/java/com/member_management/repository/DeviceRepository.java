package com.member_management.repository;

import com.member_management.modules._Device;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface DeviceRepository extends JpaRepository<_Device, Integer> {

    @Query("FROM _Device d")
    List<_Device> getAllDevices();

    @Transactional
    @Modifying
    @Query("DELETE FROM _Device d WHERE d.maTB = :deviceId")
    void deleteById(@Param("deviceId") String deviceId);

    @Query("SELECT d FROM _Device d WHERE d.maTB = :maTB")
    _Device findByMaTB(@Param("maTB") String maTB);

    @Query("FROM _Device d WHERE d.tenTB LIKE %:keyword%")
    List<_Device> searchDevicesByName(@Param("keyword") String keyword);
}
