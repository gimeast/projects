package gimeast.vehiclemanagement.vehicle.entity;

import gimeast.vehiclemanagement.common.entity.BaseEntity;
import gimeast.vehiclemanagement.member.entity.MemberEntity;
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
@Table(name="member_vehicle")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@ToString(exclude = {"member", "brand", "model", "trim"})
public class VehicleEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private String numberPlate;
    private int kilometers;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_idx")
    private MemberEntity member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_brand_idx")
    private VehicleBrandEntity brand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_model_idx")
    private VehicleModelEntity model;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_trim_idx")
    private VehicleTrimEntity trim;

    public void editKilomemters(int kilometers) {
        this.kilometers = kilometers;
    }
}
