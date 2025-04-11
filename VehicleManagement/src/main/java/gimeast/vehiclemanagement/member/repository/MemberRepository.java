package gimeast.vehiclemanagement.member.repository;

import gimeast.vehiclemanagement.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, String> {
    @Query("SELECT m FROM MemberEntity m LEFT JOIN FETCH m.roleSet WHERE m.mid = :mid")
    Optional<MemberEntity> findByMidWithRoles(@Param("mid") String mid);

    Optional<MemberEntity> findByMid(String mid);
}
