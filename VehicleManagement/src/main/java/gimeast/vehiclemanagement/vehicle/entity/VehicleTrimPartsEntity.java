package gimeast.vehiclemanagement.vehicle.entity;

import gimeast.vehiclemanagement.common.entity.BaseEntity;
import jakarta.persistence.Column;
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
@Table(name="vehicle_trim_parts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@ToString(exclude = {"trim", "parts"})
public class VehicleTrimPartsEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_trim_idx", nullable = false)
    private VehicleTrimEntity trim;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_parts_idx", nullable = false)
    private VehiclePartsEntity parts;

    @Column(nullable = false)
    private int replacementInterval;
}
