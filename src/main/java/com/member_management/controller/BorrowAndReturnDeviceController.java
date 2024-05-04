package com.member_management.controller;

import com.member_management.modules._Member;
import com.member_management.modules._UsageInformation;
import com.member_management.service.DeviceService;
import com.member_management.service.MemberService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class BorrowAndReturnDeviceController {

    private final DeviceService deviceService;
    private final MemberService memberService;

    @Autowired
    public BorrowAndReturnDeviceController(DeviceService deviceService, MemberService memberService) {
        this.deviceService = deviceService;
        this.memberService = memberService;
    }

    @GetMapping("/borrow/device")
    public String getAvailableDevices(Model model) {
        List<Object[]> devicesList = deviceService.getAvailableDevices();
        model.addAttribute("devicesList", devicesList);
        return "borrow-device";
    }

    @GetMapping("/select-member-to-borrow/{deviceId}")
    public String selectMember(Model model, @PathVariable("deviceId") String deviceId) {
        List<_Member> members = memberService.findAllUsageInformation();
        model.addAttribute("members", members);
        return "select-member-to-borrow-device";
    }

    @PostMapping("/select-member-to-borrow/{deviceId}")
    public String borrowDevice(Model model,
            RedirectAttributes redirectAttributes,
            @PathVariable("deviceId") String deviceId,
            @RequestParam("selectedMemberId") String maTV) {
        deviceService.borrowDevice(deviceId, maTV);
        redirectAttributes.addFlashAttribute("successMessage", "Mươn thiết bị " + deviceId + " thành công !");
        return "redirect:/borrow/device";
    }

    @GetMapping("/borrow/booked-device")
    public String getBookedDevices(Model model) {
        List<_UsageInformation> devicesList = deviceService.getBookedDevicesInCurrentDate();
        model.addAttribute("devicesList", devicesList);
        return "borrow-booked-device";
    }

    @PostMapping("/borrow/booked-device")
    public String borrowDevice(Model model,
            RedirectAttributes redirectAttributes,
            @RequestParam("usageInfomationId") Integer usageInfomationId,
            @RequestParam("deviceId") String deviceId,
            @RequestParam(name = "memberId") String memberId) {
        deviceService.borrowDevice(usageInfomationId, deviceId, memberId);
        redirectAttributes.addFlashAttribute("successMessage", "Mươn thiết bị " + deviceId + " thành công !");
        return "redirect:/borrow/booked-device";
    }

    @PostMapping("/borrow/{deviceId}")
    public String selectMember(Model model,
            RedirectAttributes redirectAttributes,
            @PathVariable("deviceId") String deviceId,
            @RequestParam(name = "selectedMemberId") String selectedMemberId) {
        deviceService.borrowDevice(deviceId, selectedMemberId);
        redirectAttributes.addFlashAttribute("successMessage", "Mươn thiết bị " + deviceId + " thành công !");
        return "redirect:/borrow/device";
    }

    @GetMapping("/return/device")
    public String getNotAvailableDevices(Model model) {
        List<Object[]> devicesList = deviceService.getNotAvailableDevices();
        model.addAttribute("devicesList", devicesList);
        return "return-device";
    }

    @PostMapping("/return/{deviceId}")
    public String returnDevice(RedirectAttributes redirectAttributes, @PathVariable("deviceId") String maTB) {
        deviceService.returnDevice(maTB);

        redirectAttributes.addFlashAttribute("successMessage", "Trả thiết bị " + maTB + " thành công !");
        return "redirect:/return/device";
    }
}
