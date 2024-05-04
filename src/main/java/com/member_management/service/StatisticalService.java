package com.member_management.service;

import com.member_management.modules._UsageInformation;
import com.member_management.repository.UsageInformationRepository;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatisticalService {

    private final UsageInformationRepository usageInformationRepository;

    @Autowired
    public StatisticalService(UsageInformationRepository usageInformationRepository) {
        this.usageInformationRepository = usageInformationRepository;
    }

    public List<_UsageInformation> getStudyAreaHistory(Date startDate,Date endDate) {
        return usageInformationRepository.getStudyAreaHistory(startDate, endDate);
    }
;
}
