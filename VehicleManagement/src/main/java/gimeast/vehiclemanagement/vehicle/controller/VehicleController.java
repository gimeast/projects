package gimeast.vehiclemanagement.vehicle.controller;

import gimeast.vehiclemanagement.vehicle.dto.VehicleInfoSaveDTO;
import gimeast.vehiclemanagement.vehicle.service.VehicleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/v1/vehicles")
public class VehicleController {
    private final VehicleService vehicleService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<String> saveVehicleInfoForAdmin(@RequestBody VehicleInfoSaveDTO request) {
        vehicleService.saveVehicleInfo(request);
        return new ResponseEntity<>("정보가 성공적으로 등록되었습니다.", HttpStatus.CREATED);
    }
}
