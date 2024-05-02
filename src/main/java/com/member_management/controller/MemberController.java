/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.member_management.controller;

import com.member_management.modules._Member;
import com.member_management.service.MemberService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 *
 * @author trieu
 */
@Controller
public class MemberController {
    private final MemberService memberInformationService;
    
    @Autowired
    public MemberController(MemberService memberInformationService){
        this.memberInformationService = memberInformationService;
    }
    
    @GetMapping("/member")
    public String getAllMembersInformation(Model model){
        List<_Member> membersList = memberInformationService.findAllUsageInformation();
        model.addAttribute("membersInformationList",membersList);
        return "member";
    }
    
    @PostMapping("/member")
    public String searchMembersByName(@RequestParam("keyword") String keyword, Model model) {
        List<_Member> searchResults = memberInformationService.searchMembersByName(keyword);
        model.addAttribute("membersInformationList", searchResults);
        model.addAttribute("keyword", keyword);
        return "member";
    }
    
    @GetMapping("/add-member")
    public String showAddMemberForm(){
        return "add-member";
    }
    
    @PostMapping("/add-member")
    public String addMember(@ModelAttribute _Member member, Model model) {
        if (member.getMaTV() == null || member.getMaTV().isEmpty()
                || member.getHoTen() == null || member.getHoTen().isEmpty()
                || member.getEmail() == null || member.getEmail().isEmpty()
                || member.getPassword() == null || member.getPassword().isEmpty()) {
            model.addAttribute("error", "Vui lòng điền đầy đủ thông tin!");
            return "add-member";
        }

        if (!isValidMaTV(member.getMaTV())) {
            model.addAttribute("error", "Mã thành viên không hợp lệ!");
            
            return "add-member";
        }
        memberInformationService.addMember(member);
        model.addAttribute("message", "Thêm thành viên thành công !");
        return "add-member";
    }
    
    private boolean isValidMaTV(String maTV) {
        // Kiểm tra maTV có đúng định dạng hay không (10 kí tự và là số)
        return maTV.matches("\\d{10}");
    }
    
    @PostMapping("/delete-member/{memberId}")
    public String deleteMember(@PathVariable int memberId, Model model) {
        memberInformationService.deleteMember(memberId);
        model.addAttribute("message", "Xóa thành viên thành công!");
        return "redirect:/member"; // Điều hướng về danh sách thành viên sau khi xóa
    }
    
    @GetMapping("/edit-member/{memberId}")
    public String showEditMemberForm(@PathVariable int memberId, Model model) {
        _Member member = memberInformationService.getMemberById(memberId);
        model.addAttribute("member", member);
        return "edit-member";
    }

    @PostMapping("/edit-member/{memberId}")
    public String updateMember(@PathVariable int memberId, @ModelAttribute("member") _Member updatedMember, Model model) {
        if (updatedMember.getMaTV() == null || updatedMember.getMaTV().isEmpty()
                || updatedMember.getHoTen() == null || updatedMember.getHoTen().isEmpty()
                || updatedMember.getEmail() == null || updatedMember.getEmail().isEmpty()
                || updatedMember.getPassword() == null || updatedMember.getPassword().isEmpty()) {
            model.addAttribute("error", "Vui lòng điền đầy đủ thông tin!");
            return "edit-member";
        }

        if (!isValidMaTV(updatedMember.getMaTV())) {
            model.addAttribute("error", "MaTV không hợp lệ!");
            return "edit-member";
        }
        memberInformationService.updateMember(memberId, updatedMember);
        return "redirect:/member";
    }
    
}
