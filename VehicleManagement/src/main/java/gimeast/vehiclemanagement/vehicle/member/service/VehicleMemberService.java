package gimeast.vehiclemanagement.vehicle.member.service;

import gimeast.vehiclemanagement.member.entity.MemberEntity;
import gimeast.vehiclemanagement.member.repository.MemberRepository;
import gimeast.vehiclemanagement.vehicle.admin.entity.VehicleTrimEntity;
import gimeast.vehiclemanagement.vehicle.admin.repository.VehicleTrimRepository;
import gimeast.vehiclemanagement.vehicle.member.dto.VehicleDTO;
import gimeast.vehiclemanagement.vehicle.member.entity.VehicleEntity;
import gimeast.vehiclemanagement.vehicle.member.repository.VehicleMemberRepository;
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
    private final VehicleMemberRepository vehicleMemberRepository;
    private final VehicleTrimRepository vehicleTrimRepository;

    public List<VehicleDTO> getMemberVehicleInfoList(String mid) {
        return vehicleMemberRepository.findVehicleDTOListByMid(mid);
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

        vehicleMemberRepository.save(vehicleEntity);
    }

    public void deleteVehicle(Long memberVehicleIdx) {
        vehicleMemberRepository.deleteById(memberVehicleIdx);
    }
}
