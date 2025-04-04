package gimeast.vehiclemanagement.vehicle.controller;

import gimeast.vehiclemanagement.common.dto.PageRequestDTO;
import gimeast.vehiclemanagement.common.dto.PageResponseDTO;
import gimeast.vehiclemanagement.vehicle.dto.VehicleSpecDTO;
import gimeast.vehiclemanagement.vehicle.service.VehicleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public ResponseEntity<String> specSaveForAdmin(@RequestBody VehicleSpecDTO request) {
        vehicleService.saveVehicleSpecByAdmin(request);
        return new ResponseEntity<>("정보가 성공적으로 등록되었습니다.", HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<PageResponseDTO<VehicleSpecDTO>> getSpecListForAdmin(String search, PageRequestDTO pageRequestDTO) {
        PageResponseDTO<VehicleSpecDTO> resultDTO = vehicleService.getVehicleSpecListByAdmin(search, pageRequestDTO);
        return ResponseEntity.ok(resultDTO);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/{trimIdx}")
    public ResponseEntity<VehicleSpecDTO> getSpecForAdmin(@PathVariable Long trimIdx) {
        VehicleSpecDTO details = vehicleService.getDetails(trimIdx);
        return ResponseEntity.ok(details);
    }
}
