package gimeast.vehiclemanagement.member.repository;

import gimeast.vehiclemanagement.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, String> {
    Optional<MemberEntity> findByMid(String mid);
}
