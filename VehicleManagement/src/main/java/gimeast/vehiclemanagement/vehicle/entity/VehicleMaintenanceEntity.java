package gimeast.vehiclemanagement.vehicle.entity;

import gimeast.vehiclemanagement.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="vehicle_maintenance")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@ToString(exclude = {"trimParts", "vehicle"})
public class VehicleMaintenanceEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_trim_parts_idx", nullable = false)
    private VehicleTrimPartsEntity trimParts;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_vehicle_idx", nullable = false)
    private VehicleEntity vehicle;

    private int kilometers;
    private String memo;

    public void changeMaintenance(int kilometers, String memo) {
        this.kilometers = kilometers;
        this.memo = memo;
    }
}
