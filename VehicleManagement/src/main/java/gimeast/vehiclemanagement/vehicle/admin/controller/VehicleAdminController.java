package gimeast.vehiclemanagement.vehicle.admin.controller;

import gimeast.vehiclemanagement.common.dto.PageRequestDTO;
import gimeast.vehiclemanagement.common.dto.PageResponseDTO;
import gimeast.vehiclemanagement.vehicle.dto.VehicleBrandDTO;
import gimeast.vehiclemanagement.vehicle.dto.VehicleModelDTO;
import gimeast.vehiclemanagement.vehicle.dto.VehiclePartsDTO;
import gimeast.vehiclemanagement.vehicle.dto.VehicleSpecDTO;
import gimeast.vehiclemanagement.vehicle.dto.VehicleTrimDTO;
import gimeast.vehiclemanagement.vehicle.admin.service.VehicleAdminService;
import gimeast.vehiclemanagement.vehicle.dto.VehicleTrimPartsDTO;
import gimeast.vehiclemanagement.vehicle.dto.VehicleTrimPartsRequestDTO;
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
    public ResponseEntity<String> saveSpec(@RequestBody VehicleSpecDTO vehicleSpecDTO) {
        vehicleAdminService.saveVehicleSpec(vehicleSpecDTO);
        return new ResponseEntity<>("정보가 성공적으로 등록되었습니다.", HttpStatus.CREATED);
    }

    @PostMapping("/list")
    public ResponseEntity<String> saveSpecList(@RequestBody List<VehicleSpecDTO> vehicleSpecDTOList) {
        vehicleAdminService.saveVehicleSpecList(vehicleSpecDTOList);
        return new ResponseEntity<>("정보가 성공적으로 등록되었습니다.", HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<PageResponseDTO<VehicleSpecDTO>> getSpecList(String search, PageRequestDTO pageRequestDTO) {
        PageResponseDTO<VehicleSpecDTO> resultDTO = vehicleAdminService.getVehicleSpecList(search, pageRequestDTO);
        return ResponseEntity.ok(resultDTO);
    }

    @GetMapping(value = "/{trimIdx}")
    public ResponseEntity<VehicleSpecDTO> getSpec(@PathVariable Long trimIdx) {
        VehicleSpecDTO details = vehicleAdminService.getDetails(trimIdx);
        return ResponseEntity.ok(details);
    }

    @DeleteMapping(value = "/{trimIdx}")
    public ResponseEntity<String> deleteSpec(@PathVariable Long trimIdx) {
        vehicleAdminService.deleteVehicleSpec(trimIdx);
        return ResponseEntity.ok("삭제를 완료하였습니다.");
    }

    @PostMapping(value = "/delete")
    public ResponseEntity<String> deleteSpecs(@RequestBody List<Long> trimIdx) {
        vehicleAdminService.deleteVehicleSpecs(trimIdx);
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

    @GetMapping(value = "/parts")
    public ResponseEntity<List<VehiclePartsDTO>> getParts() {
        List<VehiclePartsDTO> parts = vehicleAdminService.getVehicleParts();
        return ResponseEntity.ok(parts);
    }

    @GetMapping(value = "/trim/parts")
    public ResponseEntity<List<VehicleTrimPartsDTO>> getTrimParts(Long trimIdx) {
        List<VehicleTrimPartsDTO> list = vehicleAdminService.getVehicleTrimParts(trimIdx);
        return ResponseEntity.ok(list);
    }

    @PostMapping(value = "/parts")
    public ResponseEntity<String> saveParts(String name) {
        vehicleAdminService.saveVehicleParts(name);
        return new ResponseEntity<>("부품을 추가하였습니다.", HttpStatus.CREATED);
    }

    @PostMapping(value = "/trim/parts")
    public ResponseEntity<String> saveTrimParts(@RequestBody VehicleTrimPartsRequestDTO dto) {
        vehicleAdminService.saveVehicleTrimParts(dto.getReplacementInterval(), dto.getTrimIdx(), dto.getPartsIdx());
        return new ResponseEntity<>("해당 트림의 부품 및 교환 주기를 저장하였습니다.", HttpStatus.CREATED);
    }
}
