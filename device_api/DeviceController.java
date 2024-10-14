package Classification_project.device_api.controller;

import Classification_project.device_api.entity.Device;
import Classification_project.device_api.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/devices")
