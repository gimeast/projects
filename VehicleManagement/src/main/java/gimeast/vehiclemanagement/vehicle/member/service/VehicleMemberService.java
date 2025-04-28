package gimeast.vehiclemanagement.vehicle.member.service;

import gimeast.vehiclemanagement.common.dto.PageRequestDTO;
import gimeast.vehiclemanagement.common.dto.PageResponseDTO;
import gimeast.vehiclemanagement.member.entity.MemberEntity;
import gimeast.vehiclemanagement.member.repository.MemberRepository;
import gimeast.vehiclemanagement.vehicle.dto.VehicleBrandDTO;
import gimeast.vehiclemanagement.vehicle.dto.VehicleMaintenanceDTO;
import gimeast.vehiclemanagement.vehicle.dto.VehicleMaintenancePartsDTO;
import gimeast.vehiclemanagement.vehicle.dto.VehicleModelDTO;
import gimeast.vehiclemanagement.vehicle.dto.VehicleSpecDTO;
import gimeast.vehiclemanagement.vehicle.dto.VehicleTrimDTO;
import gimeast.vehiclemanagement.vehicle.entity.VehicleMaintenanceEntity;
import gimeast.vehiclemanagement.vehicle.entity.VehicleTrimEntity;
import gimeast.vehiclemanagement.vehicle.entity.VehicleTrimPartsEntity;
import gimeast.vehiclemanagement.vehicle.repository.VehicleBrandRepository;
import gimeast.vehiclemanagement.vehicle.repository.VehicleMaintenanceRepository;
import gimeast.vehiclemanagement.vehicle.repository.VehicleModelRepository;
import gimeast.vehiclemanagement.vehicle.repository.VehiclePartsRepository;
import gimeast.vehiclemanagement.vehicle.repository.VehicleTrimPartsRepository;
import gimeast.vehiclemanagement.vehicle.repository.VehicleTrimRepository;
import gimeast.vehiclemanagement.vehicle.dto.VehicleDTO;
import gimeast.vehiclemanagement.vehicle.entity.VehicleEntity;
import gimeast.vehiclemanagement.vehicle.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final VehicleBrandRepository vehicleBrandRepository;
    private final VehicleTrimRepository vehicleTrimRepository;
    private final VehiclePartsRepository vehiclePartsRepository;
    private final VehicleTrimPartsRepository vehicleTrimPartsRepository;
    private final VehicleMaintenanceRepository vehicleMaintenanceRepository;
    private final VehicleModelRepository vehicleModelRepository;

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

    public PageResponseDTO<VehicleMaintenanceDTO> getVehicleMaintenanceList(String search, PageRequestDTO pageRequestDTO, Long memberVehicleIdx) {
        Pageable pageable = pageRequestDTO.getPageable();

        Page<VehicleMaintenanceDTO> page = vehicleMaintenanceRepository.vehicleMaintenanceList(search, pageable, memberVehicleIdx);
        return PageResponseDTO.toPageResponse(page);
    }

    public List<VehicleBrandDTO> getBrandList() {
        return vehicleBrandRepository.findAllDTO();
    }

    public List<VehicleModelDTO> getModelListByBrandIdx(Long brandIdx) {
        return vehicleModelRepository.findAllDTOListByBrandIdx(brandIdx);
    }

    public List<VehicleTrimDTO> getTrimDTOList(Long modelIdx) {
        return vehicleTrimRepository.findAllDTOListByModelIdx(modelIdx);
    }
}
