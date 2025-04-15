package gimeast.vehiclemanagement.vehicle.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleMaintenanceDTO {
    private Long idx;
    private VehicleTrimPartsDTO parts;
    private int kilometers;
    private String memo;
    private LocalDateTime regDate;
}
