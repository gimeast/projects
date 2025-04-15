package gimeast.vehiclemanagement.vehicle.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleMaintenancePartsDTO {
    private Long trimPartsIdx;
    private String name;
}
