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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author trieu
 */
@Controller
public class MemberController {

    private final MemberService memberInformationService;

    @Autowired
    public MemberController(MemberService memberInformationService) {
        this.memberInformationService = memberInformationService;
    }

    @GetMapping("/member")
    public String getAllMembersInformation(Model model) {
        List<_Member> membersList = memberInformationService.findAllUsageInformation();
        model.addAttribute("membersInformationList", membersList);
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
    public String showAddMemberForm(Model model) {
        model.addAttribute("member", new _Member());
        return "add-member";
    }

    @PostMapping("/add-member")
    public String addMember(RedirectAttributes redirectAttributes, @ModelAttribute _Member member, Model model) {

        try {
            memberInformationService.addMember(member);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("member", member);
            return "add-member";
        }
        redirectAttributes.addFlashAttribute("successMessage", "Thêm thành viên thành công !");
        return "redirect:/member";
    }

    private boolean isValidMaTV(String maTV) {
        // Kiểm tra maTV có đúng định dạng hay không (10 kí tự và là số)
        return maTV.matches("\\d{10}");
    }

    @PostMapping("/delete-member/{memberId}")
    public String deleteMember(@PathVariable String memberId, RedirectAttributes redirectAttributes) {
        try {
            memberInformationService.deleteMember(memberId);
            redirectAttributes.addFlashAttribute("successMessage", "Xóa thành viên thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không thể xóa thành viên !");
            e.printStackTrace();
        }

        return "redirect:/member"; // Điều hướng về danh sách thành viên sau khi xóa
    }

    @GetMapping("/edit-member/{memberId}")
    public String showEditMemberForm(@PathVariable String memberId, Model model) {
        _Member member = memberInformationService.getMemberById(memberId);
        model.addAttribute("member", member);
        return "edit-member";
    }

    @PostMapping("/edit-member/{memberId}")
    public String updateMember(RedirectAttributes redirectAttributes, @PathVariable String memberId, @ModelAttribute("member") _Member updatedMember, Model model) {
        if (updatedMember.getMaTV() == null || updatedMember.getMaTV().isEmpty()
                || updatedMember.getHoTen() == null || updatedMember.getHoTen().isEmpty()
                || updatedMember.getEmail() == null || updatedMember.getEmail().isEmpty()
                || updatedMember.getPassword() == null || updatedMember.getPassword().isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Vui lòng điền đầy đủ thông tin!");
            redirectAttributes.addFlashAttribute("member", updatedMember);
            return "redicrect:/edit-member";
        }

        try {
            updatedMember.checkMaTVFormat();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            redirectAttributes.addFlashAttribute("member", updatedMember);
            return "redirect:/edit-member";
        }
        memberInformationService.updateMember(memberId, updatedMember);
        redirectAttributes.addFlashAttribute("successMessage", "Cập nhật thông tin thành viên thành công !");
        return "redirect:/member";
    }

}
