package gimeast.vehiclemanagement.vehicle.dto;

import gimeast.vehiclemanagement.vehicle.entity.VehicleBrandEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleBrandDTO {
    private Long idx;
    private String name;

    public VehicleBrandDTO(VehicleBrandEntity entity) {
        this.idx = entity.getIdx();
        this.name = entity.getName();
    }

    public VehicleBrandEntity toEntity() {
        return VehicleBrandEntity.builder()
                .idx(idx)
                .name(name)
                .build();
    }

}
