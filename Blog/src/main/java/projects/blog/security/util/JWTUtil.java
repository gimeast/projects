package projects.blog.security.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Date;

@Log4j2
public class JWTUtil {
    @Value("${gimeast.secret-key.value}")
    private String SECRET_KEY;
    private final long ACCESS_TOKEN_EXPIRE = 30; // 30분
    private final long REFRESH_TOKEN_EXPIRE = 60 * 24 * 7; // 7일
    private final MacAlgorithm hs256 = Jwts.SIG.HS256;

    public String generateAccessToken(String email) throws Exception {
        return generateToken(email, ACCESS_TOKEN_EXPIRE);
    }

    public String generateRefreshToken(String email) throws Exception {
        return generateToken(email, REFRESH_TOKEN_EXPIRE);
    }

    private String generateToken(String content, long expireMinutes) throws Exception {
        return Jwts.builder()
                .issuedAt(new Date())
                .expiration(Date.from(ZonedDateTime.now().plusMinutes(expireMinutes).toInstant()))
                .claim("sub", content)
                .signWith(getSignInKey(), hs256)
                .compact();
    }


    public String validateAndExtract(String tokenStr) throws Exception {
        String contentValue = null;

        try {
            tokenStr = tokenStr.replace("Bearer ", "");

            contentValue = Jwts.parser()
                    .verifyWith(getSignInKey())
                    .build()
                    .parseSignedClaims(tokenStr)
                    .getPayload()
                    .getSubject();
        } catch (Exception e) {
            log.error("Token validation failed: {}", e.getMessage());
        }

        return contentValue;
    }

    private SecretKey getSignInKey() {
        return new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8),
                hs256.key().build().getAlgorithm());
    }
}
