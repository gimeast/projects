package projects.blog.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projects.blog.security.entity.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByEmail(String email);
    Optional<RefreshToken> findByRefreshToken(String refreshToken);
    void deleteByEmail(String email);
}
