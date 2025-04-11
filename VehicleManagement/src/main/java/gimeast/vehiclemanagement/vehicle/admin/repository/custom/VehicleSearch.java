package gimeast.vehiclemanagement.vehicle.admin.repository.custom;

import gimeast.vehiclemanagement.vehicle.admin.dto.VehicleSpecDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VehicleSearch {
    Page<VehicleSpecDTO> list(String search, Pageable pageable);
    VehicleSpecDTO getDetails(Long trimIdx);
}
