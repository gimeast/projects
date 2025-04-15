package gimeast.vehiclemanagement.vehicle.member.service;

import gimeast.vehiclemanagement.member.entity.MemberEntity;
import gimeast.vehiclemanagement.member.repository.MemberRepository;
import gimeast.vehiclemanagement.vehicle.dto.VehicleMaintenanceDTO;
import gimeast.vehiclemanagement.vehicle.dto.VehicleMaintenancePartsDTO;
import gimeast.vehiclemanagement.vehicle.dto.VehiclePartsDTO;
import gimeast.vehiclemanagement.vehicle.entity.VehicleMaintenanceEntity;
import gimeast.vehiclemanagement.vehicle.entity.VehiclePartsEntity;
import gimeast.vehiclemanagement.vehicle.entity.VehicleTrimEntity;
import gimeast.vehiclemanagement.vehicle.entity.VehicleTrimPartsEntity;
import gimeast.vehiclemanagement.vehicle.repository.VehicleMaintenanceRepository;
import gimeast.vehiclemanagement.vehicle.repository.VehiclePartsRepository;
import gimeast.vehiclemanagement.vehicle.repository.VehicleTrimPartsRepository;
import gimeast.vehiclemanagement.vehicle.repository.VehicleTrimRepository;
import gimeast.vehiclemanagement.vehicle.dto.VehicleDTO;
import gimeast.vehiclemanagement.vehicle.entity.VehicleEntity;
import gimeast.vehiclemanagement.vehicle.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class VehicleMemberService {
    private final MemberRepository memberRepository;
    private final VehicleRepository vehicleRepository;
    private final VehicleTrimRepository vehicleTrimRepository;
    private final VehiclePartsRepository vehiclePartsRepository;
    private final VehicleTrimPartsRepository vehicleTrimPartsRepository;
    private final VehicleMaintenanceRepository vehicleMaintenanceRepository;

    public List<VehicleDTO> getMemberVehicleInfoList(String mid) {
        return vehicleRepository.findVehicleDTOListByMid(mid);
    }

    @Transactional
    public void saveVehicle(String mid, String numberPlate, int kilometers, Long trimIdx) {
        Optional<MemberEntity> memberOptional = memberRepository.findByMid(mid);
        Optional<VehicleTrimEntity> trimOptional = vehicleTrimRepository.findById(trimIdx);

        VehicleEntity vehicleEntity = VehicleEntity.builder()
                .numberPlate(numberPlate)
                .kilometers(kilometers)
                .member(memberOptional.orElseThrow())
                .brand(trimOptional.orElseThrow().getModel().getBrand())
                .model(trimOptional.orElseThrow().getModel())
                .trim(trimOptional.orElseThrow())
                .build();

        vehicleRepository.save(vehicleEntity);
    }

    public void deleteVehicle(Long memberVehicleIdx) {
        vehicleRepository.deleteById(memberVehicleIdx);
    }

    public List<VehicleMaintenancePartsDTO> getVehicleParts(Long memberVehicleIdx) {
        return vehiclePartsRepository.findAllDTOByMemberVehicleIdx(memberVehicleIdx);
    }

    @Transactional
    public void saveVehicleMaintenance(Long memberVehicleIdx, Long trimPartsIdx, int kilometers, String memo) {
        Optional<VehicleTrimPartsEntity> trimPartsOptional = vehicleTrimPartsRepository.findById(trimPartsIdx);
        Optional<VehicleEntity> vehicleOptional = vehicleRepository.findById(memberVehicleIdx);

        VehicleMaintenanceEntity maintenance = VehicleMaintenanceEntity.builder()
                .vehicle(vehicleOptional.orElseThrow())
                .trimParts(trimPartsOptional.orElseThrow())
                .kilometers(kilometers)
                .memo(memo)
                .build();

        vehicleMaintenanceRepository.save(maintenance);
    }

    public List<VehicleMaintenanceDTO> getVehicleMaintenanceList(Long memberVehicleIdx) {
        return vehicleMaintenanceRepository.findByMemberVehicleIdx(memberVehicleIdx);
    }
}
