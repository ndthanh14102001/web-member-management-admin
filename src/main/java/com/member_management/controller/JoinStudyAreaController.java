/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.member_management.controller;

import com.member_management.modules._Member;
import com.member_management.modules._UsageInformation;
import com.member_management.service.JoinStudyAreaService;
import com.member_management.service.MemberService;
import com.member_management.service.StatisticalService;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
public class JoinStudyAreaController {

    private MemberService memberInformationService;
    private JoinStudyAreaService joinStudyAreaService;

    @Autowired
    public JoinStudyAreaController(MemberService memberInformationService, JoinStudyAreaService joinStudyAreaService) {
        this.memberInformationService = memberInformationService;
        this.joinStudyAreaService = joinStudyAreaService;
    }

    @GetMapping("/join-study-area")
    public String getMembers(Model model,
            @RequestParam(name = "search-matv", required = false) String maTV) {
        List<_Member> membersList = null;
        if (maTV != null && !"".equals(maTV)) {
            membersList = memberInformationService.findAllUsageInformation(maTV);
        } else {
            membersList = memberInformationService.findAllUsageInformation();
        }
        model.addAttribute("membersInformationList", membersList);
        return "join-study-area";
    }

    @PostMapping("/join-study-area")
    public String joinStudyArea(RedirectAttributes redirectAttributes, @RequestParam(name = "maTV") String maTV) {
        joinStudyAreaService.joinStudyArea(maTV);
        redirectAttributes.addFlashAttribute("successMessage", maTV + " vào khu vực học tập thành công");
        return "redirect:/join-study-area";
    }
}
