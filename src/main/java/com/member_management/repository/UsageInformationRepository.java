package com.member_management.repository;

import com.member_management.modules._UsageInformation;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsageInformationRepository extends JpaRepository<_UsageInformation, Integer> {

    @Query("FROM _UsageInformation u "
            + "WHERE u.tGMuon IS NULL AND u.tGTra IS NULL "
            + "AND (:startTime IS NULL OR u.tGVao >= :startTime) "
            + "AND (:endTime IS NULL OR u.tGVao <= :endTime) "
            + "ORDER BY u.tGVao DESC")
    List<_UsageInformation> getStudyAreaHistory(@Param("startTime") Date startTime, @Param("endTime") Date endTime);

    @Query("""
    SELECT u.maTB.maTB,u.maTB.tenTB FROM _UsageInformation u
    WHERE u.maTB NOT IN (
        SELECT u.maTB FROM _UsageInformation u
        WHERE u.tGVao IS NULL 
           AND u.tGMuon IS NOT NULL 
           AND u.tGTra IS NULL
           AND u.tGDatCho IS NULL
    )
    GROUP BY u.maTB
    UNION
    SELECT t.maTB, t.tenTB
    FROM _Device t
    WHERE t.maTB NOT IN (
        SELECT u.maTB.maTB FROM _UsageInformation u 
        WHERE u.maTB.maTB IS NOT NULL
    )""")
    List<Object[]> getAvailableDevices();
    
    @Query("""
    SELECT u.maTB.maTB, u.maTB.tenTB, u.maTV.maTV, u.maTV.hoTen
    FROM _UsageInformation u
    WHERE u.tGVao IS NULL AND u.tGMuon IS NOT NULL AND u.tGTra IS NULL
    """)
    List<Object[]> getNotAvailableDevices();
    
    @Query("""
    SELECT u.maTB.maTB, u.maTB.tenTB, u.maTV.maTV, u.maTV.hoTen, u.tGDatCho
    FROM _UsageInformation u
    WHERE u.tGVao IS NULL 
           AND u.tGMuon IS NULL 
           AND u.tGTra IS NULL
           AND u.tGDatCho IS NOT NULL
           AND (:startTime IS NULL OR u.tGDatCho >= :startTime)
           AND (:endTime IS NULL OR u.tGDatCho <= :endTime)
    ORDER BY u.tGDatCho DESC
    """)
    List<Object[]> getBookedDevices(@Param("startTime") Date startTime, @Param("endTime") Date endTime);
}
