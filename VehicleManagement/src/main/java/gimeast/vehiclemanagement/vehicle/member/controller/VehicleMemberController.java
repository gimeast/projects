package gimeast.vehiclemanagement.vehicle.member.controller;

import gimeast.vehiclemanagement.common.dto.PageRequestDTO;
import gimeast.vehiclemanagement.common.dto.PageResponseDTO;
import gimeast.vehiclemanagement.security.auth.CustomUserPrincipal;
import gimeast.vehiclemanagement.vehicle.dto.VehicleBrandDTO;
import gimeast.vehiclemanagement.vehicle.dto.VehicleDTO;
import gimeast.vehiclemanagement.vehicle.dto.VehicleMaintenanceDTO;
import gimeast.vehiclemanagement.vehicle.dto.VehicleMaintenancePartsDTO;
import gimeast.vehiclemanagement.vehicle.dto.VehicleMaintenanceRequestDTO;
import gimeast.vehiclemanagement.vehicle.dto.VehicleModelDTO;
import gimeast.vehiclemanagement.vehicle.dto.VehiclePartsDTO;
import gimeast.vehiclemanagement.vehicle.dto.VehicleTrimDTO;
import gimeast.vehiclemanagement.vehicle.member.service.VehicleMemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vehicles")
@RequiredArgsConstructor
@Log4j2
public class VehicleMemberController {
    private final VehicleMemberService vehicleMemberService;

    @GetMapping
    public ResponseEntity<List<VehicleDTO>> getVehicleInfoList(@AuthenticationPrincipal CustomUserPrincipal user) {
        List<VehicleDTO> list = vehicleMemberService.getMemberVehicleInfoList(user.getName());
        return ResponseEntity.ok(list);
    }

    @PostMapping
    public ResponseEntity<String> saveVehicleInfo(@AuthenticationPrincipal CustomUserPrincipal user,
                                                  @RequestBody VehicleDTO vehicleDTO) {
        vehicleMemberService.saveVehicle(
                user.getName(),
                vehicleDTO.getNumberPlate(),
                vehicleDTO.getKilometers(),
                vehicleDTO.getTrim().getIdx());

        return new ResponseEntity<>("정보가 성공적으로 등록되었습니다.", HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{memberVehicleIdx}")
    public ResponseEntity<String> deleteVehicleInfo(@PathVariable Long memberVehicleIdx) {
        vehicleMemberService.deleteVehicle(memberVehicleIdx);
        return ResponseEntity.ok("삭제를 완료하였습니다.");
    }

    @GetMapping(value = "/parts")
    public ResponseEntity<List<VehicleMaintenancePartsDTO>> getParts(Long memberVehicleIdx) {
        List<VehicleMaintenancePartsDTO> list = vehicleMemberService.getVehicleParts(memberVehicleIdx);
        return ResponseEntity.ok(list);
    }

    @PostMapping("/maintenance")
    public ResponseEntity<String> saveVehicleMaintenance(@RequestBody VehicleMaintenanceRequestDTO dto) {
        vehicleMemberService.saveVehicleMaintenance(dto.getMemberVehicleIdx(), dto.getTrimPartsIdx(), dto.getKilometers(), dto.getMemo());
        return new ResponseEntity<>("정보가 성공적으로 등록되었습니다.", HttpStatus.CREATED);
    }

    @GetMapping("/maintenance")
    public ResponseEntity<PageResponseDTO<VehicleMaintenanceDTO>> getVehicleMaintenanceList(String search, PageRequestDTO pageRequestDTO, Long memberVehicleIdx) {
        PageResponseDTO<VehicleMaintenanceDTO> page = vehicleMemberService.getVehicleMaintenanceList(search, pageRequestDTO, memberVehicleIdx);
        return ResponseEntity.ok(page);
    }

    @GetMapping(value = "/brand")
    public ResponseEntity<List<VehicleBrandDTO>> getBrandList() {
        List<VehicleBrandDTO> brandList = vehicleMemberService.getBrandList();
        return ResponseEntity.ok(brandList);
    }

    @GetMapping(value = "/model")
    public ResponseEntity<List<VehicleModelDTO>> getModelList(Long brandIdx) {
        List<VehicleModelDTO> modelList = vehicleMemberService.getModelListByBrandIdx(brandIdx);
        return ResponseEntity.ok(modelList);
    }

    @GetMapping(value = "/trim")
    public ResponseEntity<List<VehicleTrimDTO>> getTrimList(Long modelIdx) {
        List<VehicleTrimDTO> trimList = vehicleMemberService.getTrimDTOList(modelIdx);
        return ResponseEntity.ok(trimList);
    }
}
