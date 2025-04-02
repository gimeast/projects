package gimeast.vehiclemanagement.security.repository;

import gimeast.vehiclemanagement.security.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByMid(String mid);
}
