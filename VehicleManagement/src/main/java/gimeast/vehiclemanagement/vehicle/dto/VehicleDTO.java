package gimeast.vehiclemanagement.vehicle.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDTO {
    private Long idx;
    private String numberPlate;
    private int kilometers;
    private VehicleBrandDTO brand;
    private VehicleModelDTO model;
    private VehicleTrimDTO trim;
}

