package gimeast.vehiclemanagement.vehicle.member.service;

import gimeast.vehiclemanagement.common.dto.PageRequestDTO;
import gimeast.vehiclemanagement.common.dto.PageResponseDTO;
import gimeast.vehiclemanagement.member.entity.MemberEntity;
import gimeast.vehiclemanagement.member.repository.MemberRepository;
import gimeast.vehiclemanagement.vehicle.dto.VehicleBrandDTO;
import gimeast.vehiclemanagement.vehicle.dto.VehicleMaintenanceDTO;
import gimeast.vehiclemanagement.vehicle.dto.VehicleMaintenancePartsDTO;
import gimeast.vehiclemanagement.vehicle.dto.VehicleModelDTO;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @Transactional
    public void deleteVehicle(Long memberVehicleIdx) {
        deleteVehicleMaintenanceByVehicleIdx(memberVehicleIdx);
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

    @Transactional
    public void editVehicleMaintenance(Long idx, int kilometers, String memo) {
        Optional<VehicleMaintenanceEntity> maintenanceOptional = vehicleMaintenanceRepository.findById(idx);
        maintenanceOptional.ifPresent(vehicleMaintenanceEntity -> vehicleMaintenanceEntity.changeMaintenance(kilometers, memo));
    }

    public void deleteVehicleMaintenance(Long idx) {
        vehicleMaintenanceRepository.deleteById(idx);
    }

    public void deleteVehicleMaintenanceByVehicleIdx(Long memberVehicleIdx) {
        vehicleMaintenanceRepository.deleteByMemberVehicleIdx(memberVehicleIdx);
    }

    @Transactional
    public List<String> intervalCalc(Long memberIdx) {
        List<String> alimList = new ArrayList<>();

        LocalDateTime oneYearAgo = LocalDateTime.now().minusYears(1);
        LocalDateTime now = LocalDateTime.now();

        vehicleRepository.findByMemberIdx(memberIdx).forEach(vehicle -> {

            List<VehicleMaintenanceEntity> maintenanceEntityList = vehicleMaintenanceRepository.findLatestMaintenance(vehicle.getIdx(), oneYearAgo, now);

            for (VehicleMaintenanceEntity maintenanceEntity : maintenanceEntityList) {
                vehicleTrimPartsRepository.findById(maintenanceEntity.getTrimParts().getIdx()).ifPresent(trimParts -> {
                    int replacementInterval = trimParts.getReplacementInterval();
                    int beforeKilometers = maintenanceEntity.getKilometers();

                    List<VehicleEntity> vehicleEntityList = vehicleRepository.findByMemberIdx(memberIdx);
                    for (VehicleEntity vehicleEntity : vehicleEntityList) {
                        int currentKilometers = vehicleEntity.getKilometers();

                        if((beforeKilometers + replacementInterval) - 1500 <=  currentKilometers) {
                            vehiclePartsRepository.findById(trimParts.getParts().getIdx()).ifPresent(parts -> {
                                alimList.add("<span style='color: red;'>※" + vehicleEntity.getNumberPlate() + " 차량의 " + parts.getName() + " 교환 시기가 다가왔습니다.</span>\n" +
                                        "<span style='font-weight: bold;'>교체 권장 주행거리: " + (beforeKilometers + replacementInterval) + "</span>\n" +
                                        "현재 주행거리: " + currentKilometers);
                            });
                        }
                    }
                });
            }
        });

        return alimList;
    }

    @Transactional
    public void modifyVehicle(Long idx, int kilometers) {
        vehicleRepository.findById(idx).ifPresent(vehicle -> {
            vehicle.editKilomemters(kilometers);
        });
    }
}
