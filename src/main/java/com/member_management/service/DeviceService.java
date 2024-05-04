package com.member_management.service;

import com.member_management.modules._Device;
import com.member_management.modules._Member;
import com.member_management.modules._UsageInformation;
import com.member_management.repository.DeviceRepository;
import com.member_management.repository.UsageInformationRepository;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceService {

    private final DeviceRepository deviceRepository;
    private final UsageInformationRepository usageInformationRepository;

    @Autowired
    public DeviceService(DeviceRepository deviceRepository, UsageInformationRepository usageInformationRepository) {
        this.deviceRepository = deviceRepository;
        this.usageInformationRepository = usageInformationRepository;
    }

    public List<_Device> findAllDevices() {
        List<_Device> devices = deviceRepository.getAllDevices();
        return devices;
    }

    public List<_Device> searchDevicecsByName(String keyword) {
        return deviceRepository.searchDevicesByName(keyword);
    }

    public void addDevice(_Device device) {
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

    public List<Object[]> getAvailableDevices() {
        return usageInformationRepository.getAvailableDevices();
    }

    public List<Object[]> getNotAvailableDevices() {
        return usageInformationRepository.getNotAvailableDevices();
    }

    public List<_UsageInformation> getBookedDevicesInCurrentDate() {

        return usageInformationRepository.getBookedDevicesAtCurrentTime();
    }

    public void borrowDevice(String maTB, String maTV) {
        Date time = new Date();
        _UsageInformation usageInformation = new _UsageInformation();
        usageInformation.setMaTV(new _Member(maTV));

        usageInformation.setMaTB(new _Device(maTB));
        usageInformation.setTGVao(null);
        usageInformation.setTGMuon(time);
        usageInformation.setTGTra(null);
        usageInformationRepository.save(usageInformation);
    }

    public void borrowDevice(int maTT, String maTB, String maTV) {
        Date time = new Date();
        _UsageInformation usageInformation = new _UsageInformation(maTT);
        usageInformation.setMaTV(new _Member(maTV));

        usageInformation.setMaTB(new _Device(maTB));
        usageInformation.setTGVao(null);
        usageInformation.setTGMuon(time);
        usageInformation.setTGTra(null);
        usageInformationRepository.save(usageInformation);
    }

    public void returnDevice(String maTB) {
        usageInformationRepository.returnDevice(maTB);
    }
}
