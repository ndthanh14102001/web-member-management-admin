/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.member_management.controller;

import com.member_management.modules._UsageInformation;
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
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author TRONG THUC
 */
@Controller
public class StatisticalController {

    private StatisticalService statisticalService;

    @Autowired
    public StatisticalController(StatisticalService statisticalService) {
        this.statisticalService = statisticalService;
    }

    @GetMapping("/")
    public String getStatistical() {
        return "redirect:/statistical/study-area";
    }

    @GetMapping("/statistical/study-area")
    public String getStudyAreaStatistical(Model model,
            @RequestParam(name = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") Date startDate,
            @RequestParam(name = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") Date endDate) {
        if (startDate == null) {
            // Nếu không có startDate được truyền, lấy startDate là ngày hiện tại trừ đi 7 ngày
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, -7);
            startDate = calendar.getTime();
        }
        if (endDate == null) {
            // Nếu không có endDate được truyền, lấy endDate là ngày hiện tại
            endDate = new Date();
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        model.addAttribute("startDate", dateFormat.format(startDate));
        model.addAttribute("endDate", dateFormat.format(endDate));

        List<_UsageInformation> studyAreaHistory = statisticalService.getStudyAreaHistory(startDate, endDate);
        model.addAttribute("studyAreaHistory", studyAreaHistory);
        model.addAttribute("totalResult", studyAreaHistory.size());
        return "study-area-statistical";
    }

    @GetMapping("/statistical/device")
    public String getDeviceStatistical(Model model,
            @RequestParam(name = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") Date startDate,
            @RequestParam(name = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") Date endDate,
            @RequestParam(name = "device-status", required = false) String deviceStatus) {
        if (startDate == null) {
            // Nếu không có startDate được truyền, lấy startDate là ngày hiện tại trừ đi 7 ngày
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, -7);
            startDate = calendar.getTime();
        }
        if (endDate == null) {
            // Nếu không có endDate được truyền, lấy endDate là ngày hiện tại
            endDate = new Date();
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        model.addAttribute("startDate", dateFormat.format(startDate));
        model.addAttribute("endDate", dateFormat.format(endDate));

        List<Object[]> devices = deviceStatus == null
                ? statisticalService.getDevices(statisticalService.STATISTICAL_AVAILABLE_DEVICES, startDate, endDate)
                : statisticalService.getDevices(Integer.parseInt(deviceStatus), startDate, endDate);
        model.addAttribute("devices", devices);
        model.addAttribute("numberOfDevice", devices.size());
        model.addAttribute("deviceStatus", deviceStatus);
        return "device-statistical";
    }
}
