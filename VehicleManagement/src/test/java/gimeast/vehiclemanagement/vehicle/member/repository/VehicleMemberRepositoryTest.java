package gimeast.vehiclemanagement.vehicle.member.repository;

import gimeast.vehiclemanagement.member.entity.MemberEntity;
import gimeast.vehiclemanagement.member.repository.MemberRepository;
import gimeast.vehiclemanagement.vehicle.admin.entity.VehicleTrimEntity;
import gimeast.vehiclemanagement.vehicle.admin.repository.VehicleTrimRepository;
import gimeast.vehiclemanagement.vehicle.member.dto.VehicleDTO;
import gimeast.vehiclemanagement.vehicle.member.entity.VehicleEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class VehicleMemberRepositoryTest {
    @Autowired
    private VehicleMemberRepository vehicleMemberRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private VehicleTrimRepository vehicleTrimRepository;

    @Test
    @Transactional
    @Commit
    void memberVehicleSave() {
//        Optional<MemberEntity> byMid = memberRepository.findByMid("test");
//        Optional<VehicleTrimEntity> byId = vehicleTrimRepository.findById(30L);
//        VehicleTrimEntity trim = byId.get();
//
//        VehicleEntity vehicle = VehicleEntity.builder()
//                .numberPlate("97고1234")
//                .kilometers(98765)
//                .member(byMid.get())
//                .brand(trim.getModel().getBrand())
//                .model(trim.getModel())
//                .trim(trim)
//                .build();
//
//        vehicleRepository.save(vehicle);

        Optional<MemberEntity> byMid = memberRepository.findByMid("test");
        Optional<VehicleTrimEntity> byId = vehicleTrimRepository.findById(14L);
        VehicleTrimEntity trim = byId.get();

        VehicleEntity vehicle = VehicleEntity.builder()
                .numberPlate("38러1234")
                .kilometers(145789)
                .member(byMid.get())
                .brand(trim.getModel().getBrand())
                .model(trim.getModel())
                .trim(trim)
                .build();

        vehicleMemberRepository.save(vehicle);
    }

    @Test
    void getMemberVehicleList() {
        List<VehicleDTO> vehicleList = vehicleMemberRepository.findVehicleDTOListByMid("test");
        for (VehicleDTO vehicleDTO : vehicleList) {
            System.out.println("vehicleDTO = " + vehicleDTO);
        }
    }
}