package com.member_management.repository;

import com.member_management.modules._UsageInformation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UsageInformationRepository extends JpaRepository<_UsageInformation, Integer> {
    @Query("SELECT u FROM _UsageInformation u JOIN FETCH u.maTV m JOIN u.maTB t WHERE t.tenTB LIKE %?1%")
    List<_UsageInformation> findAllUsageInformationWithTenTB(String tenTB);
}
