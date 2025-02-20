package projects.blog.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import net.minidev.json.JSONObject;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import projects.blog.security.dto.AuthMemberDto;
import projects.blog.security.dto.LoginRequestDto;
import projects.blog.security.entity.RefreshToken;
import projects.blog.security.repository.RefreshTokenRepository;
import projects.blog.security.util.JWTUtil;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Base64;

@Log4j2
public class ApiLoginFilter extends AbstractAuthenticationProcessingFilter {

    private final JWTUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    public ApiLoginFilter(String defaultFilterProcessesUrl, JWTUtil jwtUtil, RefreshTokenRepository refreshTokenRepository) {  // 파라미터 추가
        super(defaultFilterProcessesUrl);
        this.jwtUtil = jwtUtil;
        this.refreshTokenRepository = refreshTokenRepository;  // 추가
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        log.info("--------------------ApiLoginFilter--------------------");
        ObjectMapper objectMapper = new ObjectMapper();
        LoginRequestDto loginRequest = objectMapper.readValue(request.getInputStream(), LoginRequestDto.class);

        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        String decodedPassword = new String(Base64.getDecoder().decode(password));

        if(email == null) {
            throw new BadCredentialsException("email cannot be null");
        }

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, decodedPassword);

        return getAuthenticationManager().authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        log.info("ApiLoginFilter successfulAuthentication");
        log.info(authResult.getPrincipal());

        String email = ((AuthMemberDto) authResult.getPrincipal()).getUsername();


        try {
            String accessToken = jwtUtil.generateAccessToken(email);
            String refreshToken = jwtUtil.generateRefreshToken(email);

            // 리프레시 토큰 저장 또는 업데이트
            refreshTokenRepository.findByEmail(email)
                    .ifPresentOrElse(
                            token -> token.updateToken(refreshToken, LocalDateTime.now().plusDays(7)),
                                    () -> refreshTokenRepository.save(
                                    RefreshToken.builder()
                                            .refreshToken(refreshToken)
                                            .email(email)
                                            .expiryDate(LocalDateTime.now().plusDays(7))
                                            .build()
                            )
                    );

            response.setHeader("Authorization", "Bearer " + accessToken);
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json;charset=utf-8");

            JSONObject json = new JSONObject();
            json.put("message", "로그인 성공");
            json.put("status", "200");
            json.put("accessToken", "Bearer " + accessToken);
            json.put("refreshToken", "Bearer " + refreshToken);

            PrintWriter out = response.getWriter();
            out.print(json);
        } catch (Exception e) {
            log.error("토큰 생성 중 오류 발생: {}", e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

    }

}
