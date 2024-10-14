package Classification_project.device_api.service;

import Classification_project.device_api.entity.Device;
import Classification_project.device_api.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

@Service
public class DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    private final String FAST_API_URL = "http://127.0.0.1:8000/predict_price";

    public List<Device> getAllDevices() {
        return deviceRepository.findAll();
    }

    public Optional<Device> getDeviceById(Long id) {
        return deviceRepository.findById(id);
    }

    public Device addDevice(Device device) {
        return deviceRepository.save(device);
    }

    @Transactional
    public Device predictPrice(Long deviceId) {
        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new RuntimeException("Device not found"));

        // Prepare the request to FastAPI
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String requestJson = String.format(
                "{\"battery_power\": %d, \"blue\": %d, \"clock_speed\": %.1f, \"dual_sim\": %d, " +
                        "\"fc\": %d, \"four_g\": %d, \"int_memory\": %d, \"m_dep\": %.1f, \"mobile_wt\": %d, " +
                        "\"n_cores\": %d, \"pc\": %d, \"px_height\": %d, \"px_width\": %d, \"ram\": %d, " +
                        "\"sc_h\": %d, \"sc_w\": %d, \"talk_time\": %d, \"three_g\": %d, \"touch_screen\": %d, \"wifi\": %d}",
                device.getBatteryPower(), device.getBlue(), device.getClockSpeed(), device.getDualSim(),
                device.getFc(), device.getFourG(), device.getIntMemory(), device.getMDep(), device.getMobileWt(),
                device.getNCores(), device.getPc(), device.getPxHeight(), device.getPxWidth(), device.getRam(),
                device.getScH(), device.getScW(), device.getTalkTime(), device.getThreeG(), device.getTouchScreen(), device.getWifi()
        );

        HttpEntity<String> request = new HttpEntity<>(requestJson, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(FAST_API_URL, request, String.class);
        String predictedPriceCategory = response.getBody();

        // Update device with prediction
        device.setPredictedPriceCategory(predictedPriceCategory);
        return deviceRepository.save(device);
    }
}
