package gimeast.vehiclemanagement.vehicle.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VehiclePartsDTO {
    private Long idx;
    private String name;
    private String type;
}
