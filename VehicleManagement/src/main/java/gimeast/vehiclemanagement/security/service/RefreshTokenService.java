package gimeast.vehiclemanagement.security.service;

import gimeast.vehiclemanagement.member.dto.MemberDTO;
import gimeast.vehiclemanagement.security.entity.RefreshToken;
import gimeast.vehiclemanagement.security.repository.RefreshTokenRepository;
import gimeast.vehiclemanagement.security.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Log4j2
public class RefreshTokenService {

    @Value("${jwt.access-token.min}")
    private int jwtAccessTokenMin;

    @Value("${jwt.refresh-token.min}")
    private int jwtRefreshTokenMin;

    private final JWTUtil jwtUtil;

    private final RefreshTokenRepository refreshTokenRepository;

    /**
     * 리프레시 토큰 생성 및 저장
     * @param memberDTOResult
     * @return
     */
    @Transactional
    public Map<String, String> makeTokenMap(MemberDTO memberDTOResult) {
        String mid = memberDTOResult.getMid();
        Map<String, Object> dataMap = memberDTOResult.getDataMap();
        String accessToken = jwtUtil.createToken(dataMap, jwtAccessTokenMin);
        String refreshToken = jwtUtil.createToken(Map.of("mid", mid), jwtRefreshTokenMin);

        log.info("accessToken: {}", accessToken);
        log.info("refreshToken: {}", refreshToken);

        refreshTokenRepository.findByMid(mid)
                .ifPresentOrElse(
                        token -> token.updateToken(refreshToken, LocalDateTime.now().plusMinutes(jwtRefreshTokenMin)),
                        () -> refreshTokenRepository.save(
                                RefreshToken.builder()
                                        .refreshToken(refreshToken)
                                        .mid(mid)
                                        .expiryDate(LocalDateTime.now().plusMinutes(jwtRefreshTokenMin))
                                        .build()
                        )
                );

        return Map.of("accessToken", accessToken, "refreshToken", refreshToken);
    }

}
