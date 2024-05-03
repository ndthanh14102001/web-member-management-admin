/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.member_management.controller;

import com.member_management.modules._Member;
import com.member_management.modules._Processing;
import com.member_management.service.MemberService;
import com.member_management.service.ProcessingService;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author TRONG THUC
 */
@Controller
public class ProcessingController {

    private final ProcessingService processingRepository;
    @Autowired
    private MemberService memberService;

    @Autowired
    public ProcessingController(ProcessingService usageInformationService) {
        this.processingRepository = usageInformationService;
    }

    @GetMapping("/page_Processing")
    public String getAllUsageInformation(Model model) {
        List<_Processing> processingList = processingRepository.findAllProcessing();
        List<_Member> maTVWithProcessingList = memberService.findAllUsageInformation();

        model.addAttribute("processingList", processingList);
        model.addAttribute("maTVWithProcessingList", maTVWithProcessingList);
        Double totalAmount = processingRepository.calculateTotalAmount();
        model.addAttribute("totalAmount", totalAmount != null ? totalAmount : 0.0);
        return "page_Processing";
    }

    @PostMapping("/addProcessing")
    public String addProcessing(@RequestParam("hinhThucXL") String hinhThucXL,
            @RequestParam("soTien") Integer soTien,
            @RequestParam("ngayXL") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") Date ngayXL,
            @RequestParam("trangThaiXL") Integer trangThaiXL,
            @RequestParam("maTV") String maTV,
            RedirectAttributes redirectAttributes) {
        processingRepository.addProcessing(hinhThucXL, soTien, ngayXL, trangThaiXL, maTV);

        // Redirect or add flash attribute as needed
        redirectAttributes.addFlashAttribute("alertMessage", "Thêm xử lý thành công!");
        return "redirect:/page_Processing";
    }

    @PostMapping("/updateProcessing")
    public String updateProcessing(@RequestParam("id") String maXL,
            @RequestParam("soTien") double soTien,
            @RequestParam("trangThaiXL") int trangThaiXL, RedirectAttributes redirectAttributes) {
        // Gọi service để thực hiện cập nhật
        processingRepository.updateProcessing(maXL, soTien, trangThaiXL);
        redirectAttributes.addFlashAttribute("alertMessage", "Cập nhật xử lý thành công!");
        return "redirect:/page_Processing";
    }

    @PostMapping("/deleteProcessing")
    public String deleteProcessing(@RequestParam("id") String maXL, RedirectAttributes redirectAttributes) {
        processingRepository.deleteProcessingByMaXL(maXL);
        redirectAttributes.addFlashAttribute("alertMessage", "Xóa xử lý thành công!");
        return "redirect:/page_Processing"; // Điều hướng đến trang danh sách xử lý
    }
}
