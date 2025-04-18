package gimeast.vehiclemanagement.vehicle.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleSpecDTO {
    private VehicleBrandDTO brandDTO;
    private VehicleModelDTO modelDTO;
    private VehicleTrimDTO trimDTO;
}
