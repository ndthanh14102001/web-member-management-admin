package com.member_management.service;

import com.member_management.modules._UsageInformation;
import com.member_management.repository.UsageInformationRepository;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatisticalService {

    public final int STATISTICAL_AVAILABLE_DEVICES = 1;
    public final int STATISTICAL_NOT_AVAILABLE_DEVICES = 2;
    public final int STATISTICAL_BOOKED_DEVICES = 3;
    private final UsageInformationRepository usageInformationRepository;

    @Autowired
    public StatisticalService(UsageInformationRepository usageInformationRepository) {
        this.usageInformationRepository = usageInformationRepository;
    }

    public List<_UsageInformation> getStudyAreaHistory(Date startDate, Date endDate) {
        return usageInformationRepository.getStudyAreaHistory(startDate, endDate);
    }

    public List<Object[]> getDevices(int deviceStatus) {
        switch (deviceStatus) {
            case STATISTICAL_NOT_AVAILABLE_DEVICES -> {
                return usageInformationRepository.getNotAvailableDevices();
            }
            case STATISTICAL_BOOKED_DEVICES -> {
                return usageInformationRepository.getBookedDevices();
            }
            default -> {
                return usageInformationRepository.getAvailableDevices();
            }
        }

    }
}
