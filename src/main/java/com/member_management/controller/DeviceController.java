package com.member_management.controller;

import com.member_management.modules._Device;
import com.member_management.service.DeviceService;
import jakarta.transaction.Transactional;
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

@Controller
public class DeviceController {

    private final DeviceService deviceService;

    @Autowired
    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping("/device")
    public String getAllDevicesInformation(Model model) {
        List<_Device> devicesList = deviceService.findAllDevices();
        model.addAttribute("devicesList", devicesList);
        return "device";
    }

    @PostMapping("/device")
    public String searchDevicesByName(@RequestParam("keyword") String keyword, Model model) {
        List<_Device> searchResults = deviceService.searchDevicecsByName(keyword);
        model.addAttribute("devicesList", searchResults);
        model.addAttribute("keyword", keyword);
        return "device";
    }

    @GetMapping("/add-device")
    public String showAddDeviceForm(Model model) {
        model.addAttribute("device", new _Device());
        return "add-device";
    }

    @PostMapping("/add-device")
    public String addDevice(RedirectAttributes redirectAttributes, @ModelAttribute _Device device, Model model) {
        try {
            deviceService.addDevice(device);
        } catch (Exception e) {
            model.addAttribute("device", device);
            model.addAttribute("errorMessage", e.getMessage());
            return "add-device";
        }
        redirectAttributes.addFlashAttribute("successMessage", "Thêm thiết bị thành công !");
        return "redirect:/device";
    }

    private boolean isValidMaTB(String maTB) {
        // Kiểm tra maTB có đúng định dạng hay không (11 kí tự và là số)
        return maTB.matches("\\d{11}");
    }

    @PostMapping("/delete-device/{deviceId}")
    public String deleteDevice(@PathVariable("deviceId") String deviceId, RedirectAttributes redirectAttributes) {
        try {
            deviceService.deleteDevice(deviceId);
            redirectAttributes.addFlashAttribute("successMessage", "Xóa thiết bị thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không thể xóa thiết bị !");
        }
        return "redirect:/device"; // Điều hướng về danh sách thiết bị sau khi xóa
    }

    @GetMapping("/edit-device/{deviceId}")
    public String showEditDeviceForm(@PathVariable String deviceId, Model model) {
        _Device device = deviceService.getDeviceById(deviceId);
        model.addAttribute("device", device);
        return "edit-device";
    }

    @PostMapping("/edit-device/{deviceId}")
    public String updateDevice(@PathVariable String deviceId, @ModelAttribute("device") _Device updatedDevice, Model model) {

        if (!isValidMaTB(updatedDevice.getMaTB())) {
            model.addAttribute("error", "MaTB không hợp lệ!");
            return "edit-device";
        }
        deviceService.updateDevice(deviceId, updatedDevice);
        return "redirect:/device";
    }

}
