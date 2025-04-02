package gimeast.vehiclemanagement.security.controller;

import gimeast.vehiclemanagement.member.dto.MemberDTO;
import gimeast.vehiclemanagement.member.service.MemberService;
import gimeast.vehiclemanagement.security.service.RefreshTokenService;
import gimeast.vehiclemanagement.security.util.JWTUtil;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/token")
@Log4j2
@RequiredArgsConstructor
public class TokenController {

    private final JWTUtil jwtUtil;

    @Value("${jwt.access-token.min}")
    private int jwtAccessTokenMin;

    @Value("${jwt.refresh-token.min}")
    private int jwtRefreshTokenMin;

    private final MemberService memberService;
    private final RefreshTokenService refreshTokenService;


    @PostMapping("/make")
    public ResponseEntity<Map<String, String>> makeToken(@RequestBody MemberDTO memberDTO) {
        log.info("make token..........");

        MemberDTO memberDTOResult = memberService.read(memberDTO.getMid(), memberDTO.getMpw());
        log.info("memberDTOResult: {}", memberDTOResult);

        Map<String, String> tokenMap = refreshTokenService.makeTokenMap(memberDTOResult);
        return ResponseEntity.ok(Map.of("accessToken", tokenMap.get("accessToken"), "refreshToken", tokenMap.get("refreshToken")));
    }

    @PostMapping("/refresh")
    public ResponseEntity<Map<String, String>> refreshToken(
            @RequestHeader("Authorization") String accessTokenStr,
            @RequestParam("refreshToken") String refreshToken,
            @RequestParam("mid") String mid
    ) {
        log.info("access token with Bearer............{}", accessTokenStr);

        if(accessTokenStr == null || !accessTokenStr.startsWith("Bearer ")) {
            return handleException("No Access Token", 400); //400 Bad Request
        }

        if (refreshToken == null) {
            return handleException("No Refresh Token", 400); //400 Bad Request
        }

        log.info("refresh token.........{}", refreshToken);

        if (mid == null) {
            return handleException("No Mid", 400); //400 Bad Request
        }

        //Access Token이 만료되었는지 확인
        String accessToken = accessTokenStr.substring(7);

        try {
            //Refresh Token 검증
            jwtUtil.validateToken(accessToken);

            //아직 만료기한이 남아 있는 상황
            Map<String, String> data = makeData(mid, accessToken, refreshToken);
            log.info("Access Token is not expired..............");

            //그대로 반환
            return ResponseEntity.ok(data);
        } catch (ExpiredJwtException expiredJwtException) {
            //refresh가 필요한 상황
            try {
                //새로운 Access Token, Refresh Token 생성
                Map<String, String> newTokenMap = makeNewToken(mid, refreshToken);
                //새로운 Access Token, Refresh Token 전송
                return ResponseEntity.ok(newTokenMap);
            } catch (Exception e) {
                return handleException("REFRESH " + e.getMessage(), 400); //400 Bad Request
            }
        } catch (Exception e) {
            e.printStackTrace(); //디버깅용
            return handleException(e.getMessage(), 400); //400 Bad Request
        }
    }

    private ResponseEntity<Map<String, String>> handleException(String msg, int status) {
        return ResponseEntity.status(status).body(Map.of("error", msg));
    }

    private Map<String, String> makeData(String mid, String accessToken, String refreshToken) {
        return Map.of("mid", mid, "accessToken", accessToken, "refreshToken", refreshToken);
    }

    private Map<String, String> makeNewToken(String mid, String refreshToken) {
        Map<String, Object> claims = jwtUtil.validateToken(refreshToken);

        //Refresh Token에서 mid값 추출
        if (!mid.equals(claims.get("mid").toString())) {
            throw new RuntimeException("Invalid Refresh Token Host");
        }

        //mid를 이용해서 사용자 정보를 다시 확인한 후에 새로운 토큰 생성
        MemberDTO memberDTO = memberService.getByMid(mid);
        Map<String, String> tokenMap = refreshTokenService.makeTokenMap(memberDTO);
        return makeData(mid, tokenMap.get("accessToken"), tokenMap.get("refreshToken"));
    }
}
