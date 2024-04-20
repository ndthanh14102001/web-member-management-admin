package com.member_management.service;

import com.member_management.modules._UsageInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.member_management.repository.ExampleRepository;

@Service
public class ExampleService {

    private final ExampleRepository usageInformationRepository;

    @Autowired
    public ExampleService(ExampleRepository usageInformationRepository) {
        this.usageInformationRepository = usageInformationRepository;
    }

    public List<_UsageInformation> findAllUsageInformationWithMember() {
        List<_UsageInformation> usageInformations = usageInformationRepository.findAllUsageInformationWithTenTB("May 1");
        return usageInformations;
    }
}
