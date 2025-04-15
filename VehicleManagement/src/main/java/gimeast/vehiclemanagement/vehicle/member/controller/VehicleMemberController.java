package gimeast.vehiclemanagement.vehicle.member.controller;

import gimeast.vehiclemanagement.security.auth.CustomUserPrincipal;
import gimeast.vehiclemanagement.vehicle.dto.VehicleDTO;
import gimeast.vehiclemanagement.vehicle.member.service.VehicleMemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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
        vehicleMemberService.saveVehicle(user.getName(), vehicleDTO);
        return new ResponseEntity<>("정보가 성공적으로 등록되었습니다.", HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{memberVehicleIdx}")
    public ResponseEntity<String> deleteVehicleInfo(@PathVariable Long memberVehicleIdx) {
        vehicleMemberService.deleteVehicle(memberVehicleIdx);
        return ResponseEntity.ok("삭제를 완료하였습니다.");
    }
}
