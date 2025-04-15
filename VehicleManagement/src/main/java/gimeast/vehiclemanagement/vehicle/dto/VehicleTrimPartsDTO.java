package gimeast.vehiclemanagement.vehicle.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VehicleTrimPartsDTO {
    private Long idx;
    private VehicleTrimDTO trimDTO;
    private VehiclePartsDTO partsDTO;
    private int replacementInterval;
}
