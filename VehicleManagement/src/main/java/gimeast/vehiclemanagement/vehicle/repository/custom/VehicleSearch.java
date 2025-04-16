package gimeast.vehiclemanagement.vehicle.repository.custom;

import gimeast.vehiclemanagement.vehicle.dto.VehicleMaintenanceDTO;
import gimeast.vehiclemanagement.vehicle.dto.VehicleSpecDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VehicleSearch {
    Page<VehicleSpecDTO> vehicleSpecList(String search, Pageable pageable);
    VehicleSpecDTO getDetails(Long trimIdx);

    Page<VehicleMaintenanceDTO> vehicleMaintenanceList(String search, Pageable pageable, Long memberVehicleIdx);

}
