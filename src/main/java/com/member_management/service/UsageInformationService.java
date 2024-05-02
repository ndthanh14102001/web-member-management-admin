package com.member_management.service;

import com.member_management.repository.DeviceRepository;
import com.member_management.repository.MemberRepository;
import com.member_management.repository.ProcessingRepository;
import com.member_management.repository.UsageInformationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsageInformationService {

    private final UsageInformationRepository informationRepository;

    @Autowired
    public UsageInformationService(UsageInformationRepository informationRepository) {
        this.informationRepository = informationRepository;
    }

}
