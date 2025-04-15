package gimeast.vehiclemanagement.vehicle.repository.custom.impl;


import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
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
    public Page<VehicleSpecDTO> list(String search, Pageable pageable) {
        QVehicleBrandEntity brand = QVehicleBrandEntity.vehicleBrandEntity;
        QVehicleModelEntity model = QVehicleModelEntity.vehicleModelEntity;
        QVehicleTrimEntity trim = QVehicleTrimEntity.vehicleTrimEntity;

        BooleanExpression searchCriteria = null;
        if (search != null && !search.isEmpty()) {
            searchCriteria = brand.name.containsIgnoreCase(search)
                    .or(model.name.containsIgnoreCase(search))
                    .or(trim.drivetrain.containsIgnoreCase(search))
                    .or(trim.fuelType.containsIgnoreCase(search))
                    .or(trim.transmission.containsIgnoreCase(search));
        }

        List<VehicleSpecDTO> fetch = queryFactory.select(Projections.constructor(VehicleSpecDTO.class,
                    Projections.constructor(VehicleBrandDTO.class, brand),
                    Projections.constructor(VehicleModelDTO.class, model),
                    Projections.constructor(VehicleTrimDTO.class, trim))
            )
            .from(trim)
            .innerJoin(trim.model, model)
            .innerJoin(model.brand, brand)
            .where(searchCriteria)
            .limit(pageable.getPageSize())
            .offset(pageable.getOffset())
            .orderBy(trim.idx.desc())
            .fetch();

        Optional<Long> count = Optional.ofNullable(queryFactory.select(trim.count())
                .from(trim)
                .innerJoin(trim.model, model)
                .innerJoin(model.brand, brand)
                .where(searchCriteria)
                .fetchOne());

        return new PageImpl<>(fetch, pageable, count.orElse(0L));
    }

    @Override
    public VehicleSpecDTO getDetails(Long trimIdx) {
        QVehicleBrandEntity brand = QVehicleBrandEntity.vehicleBrandEntity;
        QVehicleModelEntity model = QVehicleModelEntity.vehicleModelEntity;
        QVehicleTrimEntity trim = QVehicleTrimEntity.vehicleTrimEntity;

        return queryFactory.select(Projections.constructor(VehicleSpecDTO.class,
                        Projections.constructor(VehicleBrandDTO.class, brand),
                        Projections.constructor(VehicleModelDTO.class, model),
                        Projections.constructor(VehicleTrimDTO.class, trim)))
                .from(trim)
                .innerJoin(trim.model, model)
                .innerJoin(model.brand, brand)
                .where(trim.idx.eq(trimIdx))
                .fetchOne();
    }
}
