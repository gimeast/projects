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
import projects.blog.security.repository.RefreshTokenRepository;
import projects.blog.security.util.JWTUtil;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Log4j2
public class AuthController {

    private final JWTUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    @PostMapping("/refresh")
    public ResponseEntity<TokenDto> refresh(@RequestHeader("Authorization") String refreshToken) {
        try {
            log.info("리프레시토큰 발급!!!");
            String email = jwtUtil.validateAndExtract(refreshToken);
            if (email != null) {
                String newAccessToken = "Bearer " + jwtUtil.generateAccessToken(email);
//                String newRefreshToken = jwtUtil.generateRefreshToken(email);

                return ResponseEntity.ok(TokenDto.builder()
                        .accessToken(newAccessToken)
//                        .refreshToken(newRefreshToken)
                        .build());
            }
        } catch (Exception e) {
            log.error("Refresh token validation failed: {}", e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }


}
