package com.member_management.service;

import com.member_management.modules._Member;
import com.member_management.modules._UsageInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.member_management.repository.UsageInformationRepository;
import java.util.Date;

@Service
public class JoinStudyAreaService {

    private final UsageInformationRepository usageInformationRepository;

    @Autowired
    public JoinStudyAreaService(UsageInformationRepository usageInformationRepository) {
        this.usageInformationRepository = usageInformationRepository;
    }

    public void joinStudyArea(String memberId) {
        Date time = new Date();
        _UsageInformation usageInformation = new _UsageInformation();
        usageInformation.setMaTV(new _Member(memberId));

        usageInformation.setMaTB(null);
        usageInformation.setTGVao(time);
        usageInformation.setTGMuon(null);
        usageInformation.setTGTra(null);
        usageInformationRepository.save(usageInformation);
    }
}
