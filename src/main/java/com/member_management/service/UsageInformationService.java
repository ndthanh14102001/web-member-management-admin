package com.member_management.service;

import com.member_management.modules._UsageInformation;
import com.member_management.repository.UsageInformationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UsageInformationService {

    private final UsageInformationRepository usageInformationRepository;

    @Autowired
    public UsageInformationService(UsageInformationRepository usageInformationRepository) {
        this.usageInformationRepository = usageInformationRepository;
    }

    public List<_UsageInformation> findAllUsageInformationWithMember() {
        List<_UsageInformation> usageInformations = usageInformationRepository.findAllUsageInformationWithTenTB("May 1");
        return usageInformations;
    }
}
