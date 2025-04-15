package gimeast.vehiclemanagement.vehicle.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleMaintenanceRequestDTO {
    private Long idx;
    private Long trimPartsIdx;
    private Long memberVehicleIdx;
    private int kilometers;
    private String memo;
}
