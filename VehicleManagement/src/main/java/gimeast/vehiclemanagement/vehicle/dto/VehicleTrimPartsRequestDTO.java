package gimeast.vehiclemanagement.vehicle.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VehicleTrimPartsRequestDTO {
    private Long idx;
    private Long trimIdx;
    private Long partsIdx;
    private int replacementInterval;
}
