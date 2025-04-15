package gimeast.vehiclemanagement.vehicle.member.service;

import gimeast.vehiclemanagement.member.entity.MemberEntity;
import gimeast.vehiclemanagement.member.repository.MemberRepository;
import gimeast.vehiclemanagement.vehicle.entity.VehicleTrimEntity;
import gimeast.vehiclemanagement.vehicle.repository.VehicleTrimRepository;
import gimeast.vehiclemanagement.vehicle.dto.VehicleDTO;
import gimeast.vehiclemanagement.vehicle.entity.VehicleEntity;
import gimeast.vehiclemanagement.vehicle.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class VehicleMemberService {
    private final MemberRepository memberRepository;
    private final VehicleRepository vehicleRepository;
    private final VehicleTrimRepository vehicleTrimRepository;

    public List<VehicleDTO> getMemberVehicleInfoList(String mid) {
        return vehicleRepository.findVehicleDTOListByMid(mid);
    }

    @Transactional
    public void saveVehicle(String mid, VehicleDTO vehicleDTO) {
        Optional<MemberEntity> memberOptional = memberRepository.findByMid(mid);
        Optional<VehicleTrimEntity> trimOptional = vehicleTrimRepository.findById(vehicleDTO.getTrim().getIdx());

        VehicleEntity vehicleEntity = VehicleEntity.builder()
                .numberPlate(vehicleDTO.getNumberPlate())
                .kilometers(vehicleDTO.getKilometers())
                .member(memberOptional.orElseThrow())
                .brand(trimOptional.orElseThrow().getModel().getBrand())
                .model(trimOptional.orElseThrow().getModel())
                .trim(trimOptional.orElseThrow())
                .build();

        vehicleRepository.save(vehicleEntity);
    }

    public void deleteVehicle(Long memberVehicleIdx) {
        vehicleRepository.deleteById(memberVehicleIdx);
    }
}
