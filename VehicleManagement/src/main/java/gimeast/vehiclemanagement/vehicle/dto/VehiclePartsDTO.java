package gimeast.vehiclemanagement.vehicle.dto;

import gimeast.vehiclemanagement.vehicle.entity.VehiclePartsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehiclePartsDTO {
    private Long idx;
    private String name;

    public VehiclePartsDTO(VehiclePartsEntity entity) {
        this.idx = entity.getIdx();
        this.name = entity.getName();
    }
}
