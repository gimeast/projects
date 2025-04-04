package gimeast.vehiclemanagement.vehicle.service;

import gimeast.vehiclemanagement.vehicle.dto.VehicleBrandDTO;
import gimeast.vehiclemanagement.vehicle.dto.VehicleSpecDTO;
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
    void saveVehicleSpecByAdmin() {
        VehicleSpecDTO vehicleSpecDTO = new VehicleSpecDTO();
        vehicleSpecDTO.setBrandDTO(new VehicleBrandDTO(null, "HYUNDAI"));
        vehicleSpecDTO.setModelDTO(new VehicleModelDTO(null, "Grandeur", "2025", null));
        vehicleSpecDTO.setTrimDTO(new VehicleTrimDTO(null, null, "2WD", "gasoline", "DCT8"));

        vehicleService.saveVehicleSpecByAdmin(vehicleSpecDTO);
    }
}