package gimeast.vehiclemanagement.vehicle.repository.custom.impl;


import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gimeast.vehiclemanagement.vehicle.dto.VehicleBrandDTO;
import gimeast.vehiclemanagement.vehicle.dto.VehicleSpecDTO;
import gimeast.vehiclemanagement.vehicle.dto.VehicleModelDTO;
import gimeast.vehiclemanagement.vehicle.dto.VehicleTrimDTO;
import gimeast.vehiclemanagement.vehicle.entity.QVehicleBrandEntity;
import gimeast.vehiclemanagement.vehicle.entity.QVehicleModelEntity;
import gimeast.vehiclemanagement.vehicle.entity.QVehicleTrimEntity;
import gimeast.vehiclemanagement.vehicle.repository.custom.VehicleSearch;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
public class VehicleSearchImpl implements VehicleSearch {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<VehicleSpecDTO> list(Pageable pageable) {
        QVehicleBrandEntity brand = QVehicleBrandEntity.vehicleBrandEntity;
        QVehicleModelEntity model = QVehicleModelEntity.vehicleModelEntity;
        QVehicleTrimEntity trim = QVehicleTrimEntity.vehicleTrimEntity;

        List<VehicleSpecDTO> fetch = queryFactory.select(Projections.constructor(VehicleSpecDTO.class,
                        Projections.constructor(VehicleBrandDTO.class, brand),
                        Projections.constructor(VehicleModelDTO.class, model),
                        Projections.constructor(VehicleTrimDTO.class, trim))
                )
                .from(trim)
                .innerJoin(trim.model, model)
                .innerJoin(model.brand, brand)
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        Optional<Long> count = Optional.ofNullable(queryFactory.select(trim.count())
                .from(trim)
                .innerJoin(trim.model, model)
                .innerJoin(model.brand, brand)
                .fetchOne());

        return new PageImpl<>(fetch, pageable, count.orElse(0L));
    }
}
