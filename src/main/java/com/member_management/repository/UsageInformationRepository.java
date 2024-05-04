package com.member_management.repository;

import com.member_management.modules._UsageInformation;
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
}
