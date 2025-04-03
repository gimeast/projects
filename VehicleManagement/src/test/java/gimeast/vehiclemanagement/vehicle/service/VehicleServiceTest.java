package gimeast.vehiclemanagement.vehicle.service;

import gimeast.vehiclemanagement.vehicle.dto.VehicleBrandDTO;
import gimeast.vehiclemanagement.vehicle.dto.VehicleInfoSaveDTO;
import gimeast.vehiclemanagement.vehicle.dto.VehicleModelDTO;
import gimeast.vehiclemanagement.vehicle.dto.VehicleTrimDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class VehicleServiceTest {

    @Autowired
    private VehicleService vehicleService;

    /**
     * 자동차 회사 브랜드, 차종, 트림 저장
     */
    @Test
    void saveVehicleInfo() {
        VehicleInfoSaveDTO vehicleInfoSaveDTO = new VehicleInfoSaveDTO();
        vehicleInfoSaveDTO.setBrandDTO(new VehicleBrandDTO(null, "HYUNDAI"));
        vehicleInfoSaveDTO.setModelDTO(new VehicleModelDTO(null, "Grandeur", "2025", null));
        vehicleInfoSaveDTO.setTrimDTO(new VehicleTrimDTO(null, null, "2WD", "gasoline", "DCT8"));

        vehicleService.saveVehicleInfo(vehicleInfoSaveDTO);
    }
}