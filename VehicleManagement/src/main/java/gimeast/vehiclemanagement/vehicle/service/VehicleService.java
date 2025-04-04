package gimeast.vehiclemanagement.vehicle.service;

import gimeast.vehiclemanagement.common.dto.PageRequestDTO;
import gimeast.vehiclemanagement.common.dto.PageResponseDTO;
import gimeast.vehiclemanagement.vehicle.dto.VehicleSpecDTO;
import gimeast.vehiclemanagement.vehicle.dto.VehicleTrimDTO;
import gimeast.vehiclemanagement.vehicle.entity.VehicleBrandEntity;
import gimeast.vehiclemanagement.vehicle.entity.VehicleModelEntity;
import gimeast.vehiclemanagement.vehicle.entity.VehicleTrimEntity;
import gimeast.vehiclemanagement.vehicle.exception.TrimAlreadyExistsException;
import gimeast.vehiclemanagement.vehicle.repository.VehicleBrandRepository;
import gimeast.vehiclemanagement.vehicle.repository.VehicleModelRepository;
import gimeast.vehiclemanagement.vehicle.repository.VehiclePartsRepository;
import gimeast.vehiclemanagement.vehicle.repository.VehicleTrimPartsRepository;
import gimeast.vehiclemanagement.vehicle.repository.VehicleTrimRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Log4j2
public class VehicleService {
    private final VehicleBrandRepository vehicleBrandRepository;
    private final VehicleModelRepository vehicleModelRepository;
    private final VehiclePartsRepository vehiclePartsRepository;
    private final VehicleTrimPartsRepository vehicleTrimPartsRepository;
    private final VehicleTrimRepository vehicleTrimRepository;

    @Transactional
    public void saveVehicleSpecByAdmin(VehicleSpecDTO vehicleSpecDTO) {
        String brandName = vehicleSpecDTO.getBrandDTO().getName();

        VehicleBrandEntity brandEntity = vehicleBrandRepository.findByName(brandName)
                .orElseGet(() -> vehicleBrandRepository.save(vehicleSpecDTO.getBrandDTO().toEntity()));

        String modelName = vehicleSpecDTO.getModelDTO().getName();
        VehicleModelEntity modelEntity = vehicleModelRepository.findByNameAndBrand(modelName, brandEntity)
                .orElseGet(() -> {
                    VehicleModelEntity newModel = vehicleSpecDTO.getModelDTO().toEntity();
                    newModel.setBrand(brandEntity);
                    return vehicleModelRepository.save(newModel);
                });

        VehicleTrimDTO trimDTO = vehicleSpecDTO.getTrimDTO();
        vehicleTrimRepository.findByModelAndDrivetrainAndFuelTypeAndTransmission(
                        modelEntity,
                        trimDTO.getDrivetrain(),
                        trimDTO.getFuelType(),
                        trimDTO.getTransmission()
                )
                .ifPresent(entity -> {
                    throw new TrimAlreadyExistsException("이미 존재하는 트림입니다. [모델: " + modelEntity.getName() + ", 구동방식: " + trimDTO.getDrivetrain()
                            + ", 엔진유형: " + trimDTO.getFuelType() + ", 변속기: " + trimDTO.getTransmission() + "]");
                });

        VehicleTrimEntity newTrimEntity = trimDTO.toEntity();
        newTrimEntity.setModel(modelEntity);
        vehicleTrimRepository.save(newTrimEntity);
    }

    public PageResponseDTO<VehicleSpecDTO> getVehicleSpecListByAdmin(PageRequestDTO pageRequestDTO) {
        Sort sort = Sort.by("mno").descending();
        Pageable pageable = pageRequestDTO.getPageable(sort);

        Page<VehicleSpecDTO> page = vehicleTrimRepository.list(pageable);
        return PageResponseDTO.toPageResponse(page);
    }
}
