package projects.blog.security.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import projects.blog.security.dto.TokenDto;
import projects.blog.security.entity.RefreshToken;
import projects.blog.security.repository.RefreshTokenRepository;
import projects.blog.security.util.JWTUtil;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Log4j2
public class AuthController {

    private final JWTUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestHeader("Authorization") String refreshToken) {
        try {
            if (refreshToken != null && refreshToken.startsWith("Bearer ")) {
                String token = refreshToken.substring(7);
                String email = jwtUtil.validateAndExtract(token);

                if (email != null) {
                    Optional<RefreshToken> savedRefreshToken = refreshTokenRepository.findByRefreshToken(token);

                    if (savedRefreshToken.isPresent() && savedRefreshToken.get().getExpiryDate().isAfter(LocalDateTime.now())) {

                        String newAccessToken = jwtUtil.generateAccessToken(email);

                        return ResponseEntity.ok(TokenDto.builder()
                                .grantType("Bearer")
                                .accessToken(newAccessToken)
                                .refreshToken(token)
                                .build());
                    }
                }
            }

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            log.error("Refresh token error: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
