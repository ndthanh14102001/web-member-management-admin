/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.member_management.controller;

import com.member_management.modules._Member;
import com.member_management.modules._Processing;
import com.member_management.service.MemberService;
import com.member_management.service.ProcessingService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    @GetMapping("/processing")
    public String getAllUsageInformation(Model model) {
        List<_Processing> processingList = processingRepository.findAllProcessing();
        List<_Member> maTVWithProcessingList = memberService.findAllUsageInformation();

        model.addAttribute("processingList", processingList);
        model.addAttribute("maTVWithProcessingList", maTVWithProcessingList);
        Double totalAmount = processingRepository.calculateTotalAmount();
        model.addAttribute("totalAmount", totalAmount != null ? totalAmount : 0.0);
        return "processing";
    }

    @GetMapping("/add-processing")
    public String addProcessing(Model model) {
        return "add-processing";
    }

    @PostMapping("/add-processing")
    public String addProcessing(@RequestParam("hinhthucxuly") String hinhThucXL,
            @RequestParam(name = "soTien", required = false) Integer soTien,
            @RequestParam("ngayxuly") String ngayXL,
            @RequestParam("trangthai") String trangThaiXL,
            @RequestParam("maTV") String maTV,
            RedirectAttributes redirectAttributes) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = dateFormat.parse(ngayXL);
            processingRepository.addProcessing(hinhThucXL, soTien, date, Integer.valueOf(trangThaiXL), maTV);
        } catch (ParseException ex) {
            Logger.getLogger(ProcessingController.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Redirect or add flash attribute as needed
        redirectAttributes.addFlashAttribute("alertMessage", "Thêm xử lý thành công!");
        return "redirect:/processing";
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
