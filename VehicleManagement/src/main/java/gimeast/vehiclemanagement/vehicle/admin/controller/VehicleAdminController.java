package gimeast.vehiclemanagement.vehicle.admin.controller;

import gimeast.vehiclemanagement.common.dto.PageRequestDTO;
import gimeast.vehiclemanagement.common.dto.PageResponseDTO;
import gimeast.vehiclemanagement.vehicle.admin.dto.VehicleBrandDTO;
import gimeast.vehiclemanagement.vehicle.admin.dto.VehicleModelDTO;
import gimeast.vehiclemanagement.vehicle.admin.dto.VehicleSpecDTO;
import gimeast.vehiclemanagement.vehicle.admin.dto.VehicleTrimDTO;
import gimeast.vehiclemanagement.vehicle.admin.service.VehicleAdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/v1/admin/vehicles")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class VehicleAdminController {
    private final VehicleAdminService vehicleAdminService;

    @PostMapping
    public ResponseEntity<String> saveSpecByAdmin(@RequestBody VehicleSpecDTO request) {
        vehicleAdminService.saveVehicleSpecByAdmin(request);
        return new ResponseEntity<>("정보가 성공적으로 등록되었습니다.", HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<PageResponseDTO<VehicleSpecDTO>> getSpecListByAdmin(String search, PageRequestDTO pageRequestDTO) {
        PageResponseDTO<VehicleSpecDTO> resultDTO = vehicleAdminService.getVehicleSpecListByAdmin(search, pageRequestDTO);
        return ResponseEntity.ok(resultDTO);
    }

    @GetMapping(value = "/{trimIdx}")
    public ResponseEntity<VehicleSpecDTO> getSpecByAdmin(@PathVariable Long trimIdx) {
        VehicleSpecDTO details = vehicleAdminService.getDetails(trimIdx);
        return ResponseEntity.ok(details);
    }

    @DeleteMapping(value = "/{trimIdx}")
    public ResponseEntity<String> deleteSpecByAdmin(@PathVariable Long trimIdx) {
        vehicleAdminService.deleteVehicleSpecByAdmin(trimIdx);
        return ResponseEntity.ok("삭제를 완료하였습니다.");
    }

    @PostMapping(value = "/delete")
    public ResponseEntity<String> deleteSpecsByAdmin(@RequestBody List<Long> trimIdx) {
        vehicleAdminService.deleteVehicleSpecsByAdmin(trimIdx);
        return ResponseEntity.ok("삭제를 완료하였습니다.");
    }

    @GetMapping(value = "/brand")
    public ResponseEntity<List<VehicleBrandDTO>> getBrandList() {
        List<VehicleBrandDTO> brandList = vehicleAdminService.getBrandList();
        return ResponseEntity.ok(brandList);
    }

    @GetMapping(value = "/model")
    public ResponseEntity<List<VehicleModelDTO>> getModelList(Long brandIdx) {
        List<VehicleModelDTO> modelList = vehicleAdminService.getModelListByBrandIdx(brandIdx);
        return ResponseEntity.ok(modelList);
    }

    @GetMapping(value = "/trim")
    public ResponseEntity<List<VehicleTrimDTO>> getTrimList(Long modelIdx) {
        List<VehicleTrimDTO> trimList = vehicleAdminService.getTrimDTOList(modelIdx);
        return ResponseEntity.ok(trimList);
    }
}
