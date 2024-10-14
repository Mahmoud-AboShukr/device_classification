package Classification_project.device_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import Classification_project.device_api.entity.Device;

public interface DeviceRepository extends JpaRepository<Device, Long> {
}
