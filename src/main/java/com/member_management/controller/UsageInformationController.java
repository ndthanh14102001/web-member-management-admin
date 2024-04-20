package com.member_management.controller;
import com.member_management.modules._UsageInformation;
import com.member_management.service.UsageInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
public class UsageInformationController {

    private final UsageInformationService usageInformationService;

    @Autowired
    public UsageInformationController(UsageInformationService usageInformationService) {
        this.usageInformationService = usageInformationService;
    }

    @GetMapping("/usage-information")
    public String getAllUsageInformation(Model model) {
        List<_UsageInformation> usageInformationList = usageInformationService.findAllUsageInformationWithMember();
        model.addAttribute("usageInformationList", usageInformationList);
        return "usage-information";
    }
}
