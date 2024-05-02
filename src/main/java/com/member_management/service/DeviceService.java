package com.member_management.service;

import com.member_management.modules._Device;
import com.member_management.repository.DeviceRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceService {

    private final DeviceRepository deviceRepository;

    @Autowired
    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }
    
    public List<_Device> findAllDevices() {
        List<_Device> devices = deviceRepository.getAllDevices();
        return devices;
    }
    
    public List<_Device> searchDevicecsByName(String keyword) {
        return deviceRepository.searchDevicesByName(keyword);
    }
    
    public void addDevice(_Device device){
        deviceRepository.save(device);
    }
    
    public void deleteDevice(String deviceId) {
        deviceRepository.deleteById(deviceId);
    }
    
    public _Device getDeviceById(String deviceId) {
        return deviceRepository.findByMaTB(deviceId);
    }

    public void updateDevice(String deviceId, _Device updatedDevice) {
        _Device existingDevice = deviceRepository.findByMaTB(deviceId);
        if (existingDevice != null) {
            existingDevice.setTenTB(updatedDevice.getTenTB());
            existingDevice.setMoTaTB(updatedDevice.getMoTaTB());
            deviceRepository.save(existingDevice);
        }
    }
}
