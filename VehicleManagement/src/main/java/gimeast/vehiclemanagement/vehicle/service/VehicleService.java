package gimeast.vehiclemanagement.vehicle.service;

import gimeast.vehiclemanagement.common.dto.PageRequestDTO;
import gimeast.vehiclemanagement.common.dto.PageResponseDTO;
import gimeast.vehiclemanagement.common.exception.EntityNotFoundException;
import gimeast.vehiclemanagement.vehicle.dto.VehicleSpecDTO;
import gimeast.vehiclemanagement.vehicle.dto.VehicleTrimDTO;
import gimeast.vehiclemanagement.vehicle.entity.VehicleBrandEntity;
import gimeast.vehiclemanagement.vehicle.entity.VehicleModelEntity;
import gimeast.vehiclemanagement.vehicle.entity.VehicleTrimEntity;
import gimeast.vehiclemanagement.vehicle.exception.AlreadyExistsException;
import gimeast.vehiclemanagement.vehicle.repository.VehicleBrandRepository;
import gimeast.vehiclemanagement.vehicle.repository.VehicleModelRepository;
import gimeast.vehiclemanagement.vehicle.repository.VehiclePartsRepository;
import gimeast.vehiclemanagement.vehicle.repository.VehicleTrimPartsRepository;
import gimeast.vehiclemanagement.vehicle.repository.VehicleTrimRepository;
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
public class VehicleService {
    private final VehicleBrandRepository vehicleBrandRepository;
    private final VehicleModelRepository vehicleModelRepository;
    private final VehicleTrimRepository vehicleTrimRepository;
    private final VehiclePartsRepository vehiclePartsRepository;
    private final VehicleTrimPartsRepository vehicleTrimPartsRepository;

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
                    throw new AlreadyExistsException("이미 존재하는 트림입니다. [모델: " + modelEntity.getName() + ", 구동방식: " + trimDTO.getDrivetrain()
                            + ", 엔진유형: " + trimDTO.getFuelType() + ", 변속기: " + trimDTO.getTransmission() + "]");
                });

        VehicleTrimEntity newTrimEntity = trimDTO.toEntity();
        newTrimEntity.setModel(modelEntity);
        vehicleTrimRepository.save(newTrimEntity);
    }

    public PageResponseDTO<VehicleSpecDTO> getVehicleSpecListByAdmin(String search, PageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.getPageable();

        Page<VehicleSpecDTO> page = vehicleTrimRepository.list(search, pageable);
        return PageResponseDTO.toPageResponse(page);
    }

    public VehicleSpecDTO getDetails(Long trimIdx) {
        return vehicleTrimRepository.getDetails(trimIdx);
    }

    @Transactional
    public void deleteVehicleSpecByAdmin(Long trimIdx) {
        Optional<VehicleTrimEntity> byId = vehicleTrimRepository.findById(trimIdx);
        if(byId.isEmpty()) {
            throw new EntityNotFoundException("존재하지 않는 데이터입니다.");
        }

        VehicleTrimEntity trim = byId.get();

        VehicleModelEntity model = trim.getModel();
        int modelCount = vehicleTrimRepository.countByModel(model);

        VehicleBrandEntity brand = model.getBrand();
        int brandCount = vehicleModelRepository.countByBrand(brand);

        vehicleTrimRepository.deleteById(trim.getIdx());

        if(modelCount == 1) {
            vehicleModelRepository.deleteById(model.getIdx());

            if(brandCount == 1) {
                vehicleBrandRepository.deleteById(brand.getIdx());
            }
        }
    }

    @Transactional
    public void deleteVehicleSpecsByAdmin(List<Long> trimIdx) {
        for (Long idx : trimIdx) {
            deleteVehicleSpecByAdmin(idx);
        }
    }
}
