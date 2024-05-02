package com.member_management.service;

import com.member_management.modules._Member;
import com.member_management.modules._Processing;
import com.member_management.repository.DeviceRepository;
import com.member_management.repository.MemberRepository;
import com.member_management.repository.ProcessingRepository;
import jakarta.transaction.Transactional;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProcessingService {

    private final ProcessingRepository processingRepository;

    @Autowired
    public ProcessingService(ProcessingRepository processingRepository) {
        this.processingRepository = processingRepository;
    }

    public List<_Processing> findAllProcessing() {
        List<_Processing> usageInformations = processingRepository.findAllProcessing();
        return usageInformations;
    }

    public Double calculateTotalAmount() {
        return processingRepository.getTotalAmount();
    }

    public _Processing addProcessing(String hinhThucXL, Integer soTien, Date ngayXL, Integer trangThaiXL, String maTV) {
        _Processing newProcessing = new _Processing();
        newProcessing.setMaTV(new _Member(maTV));
        newProcessing.setHinhThucXL(hinhThucXL);
        newProcessing.setSoTien(soTien);
        newProcessing.setNgayXL(ngayXL);
        newProcessing.setTrangThaiXL(trangThaiXL);
        return processingRepository.save(newProcessing);
    }

    @Transactional
    public void updateProcessing(String maXL, double soTien, int trangThaiXL) {
        processingRepository.updateProcessing(soTien, trangThaiXL, maXL);
    }

    @Transactional
    public void deleteProcessingByMaXL(String maXL) {
        processingRepository.deleteByMaXL(maXL);
    }
}
