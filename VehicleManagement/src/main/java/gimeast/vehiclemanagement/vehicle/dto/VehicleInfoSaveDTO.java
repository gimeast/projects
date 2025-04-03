package gimeast.vehiclemanagement.vehicle.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VehicleInfoSaveDTO {
    private VehicleBrandDTO brandDTO;
    private VehicleModelDTO modelDTO;
    private VehicleTrimDTO trimDTO;
}
