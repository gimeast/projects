package gimeast.vehiclemanagement.vehicle.repository.custom;

import gimeast.vehiclemanagement.vehicle.dto.VehicleSpecDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VehicleSearch {
    Page<VehicleSpecDTO> list(String search, Pageable pageable);
}
