package gimeast.vehiclemanagement.vehicle.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleTrimPartsDTO {
    private Long idx;
    private int replacementInterval;
    private VehicleTrimDTO trimDTO;
    private VehiclePartsDTO partsDTO;
}
