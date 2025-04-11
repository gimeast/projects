package gimeast.vehiclemanagement.vehicle.admin.dto;

import gimeast.vehiclemanagement.vehicle.admin.entity.VehicleModelEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleModelDTO {
    private Long idx;
    private String name;
    private String year;

    public VehicleModelDTO(VehicleModelEntity entity) {
        this.idx = entity.getIdx();
        this.name = entity.getName();
        this.year = entity.getYear();
    }

    public VehicleModelEntity toEntity() {
        return VehicleModelEntity.builder()
                .name(name)
                .year(year)
                .build();
    }
}
