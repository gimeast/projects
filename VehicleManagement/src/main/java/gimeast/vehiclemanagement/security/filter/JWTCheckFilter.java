package gimeast.vehiclemanagement.security.filter;

import gimeast.vehiclemanagement.member.entity.MemberEntity;
import gimeast.vehiclemanagement.member.entity.MemberRole;
import gimeast.vehiclemanagement.member.repository.MemberRepository;
import gimeast.vehiclemanagement.security.auth.CustomUserPrincipal;
import gimeast.vehiclemanagement.security.util.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Log4j2
public class JWTCheckFilter extends OncePerRequestFilter {
    private final JWTUtil jwtUtil;

    private final MemberRepository memberRepository;

    /**
     * JWTCheckFilter가 동작하지 않아야 하는 경로를 지정하기 위해 사용
     * @param request
     * @return
     * @throws ServletException
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();

        if (path.startsWith("/api/v1/token/") || path.equals("/api/v1/members/join") ) {
            return true;
        }

        if (!path.startsWith("/api/")) {
            return true;
        }

        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("JWTCheckFilter doFilter.........");
        log.info("requestURI: {}", request.getRequestURI());

        String headerStr = request.getHeader("Authorization");
        log.info("headerStr: {}", headerStr);

        //Access Token이 없는 경우
        if (headerStr == null || !headerStr.startsWith("Bearer ")) {
            handleException(response, new Exception("ACCESS TOKEN NOT FOUND"));
            return;
        }

        String accessToken = headerStr.substring(7);
        try {
            Map<String, Object> tokenMap = jwtUtil.validateToken(accessToken);
            //토큰 검증 결과에 문제가 없다면
            log.info("tokenMap: {}", tokenMap);

            String mid = tokenMap.get("mid").toString();
            Optional<MemberEntity> byMid = memberRepository.findByMidWithRoles(mid);

            if (byMid.isEmpty()) {
                handleException(response, new Exception("MEMBER NOT FOUND"));
                return;
            }

            String[] roles = byMid.get().getRoleSet().stream()
                    .map(Enum::name)
                    .toArray(String[]::new);

            //토큰 검증 결과를 이용해서 Authentication 객체를 생성
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(
                            new CustomUserPrincipal(mid),
                            null,
                            Arrays.stream(roles)
                                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                                    .collect(Collectors.toList())
                    );

            //SecurityContextHolder에 Authentication 객체를 저장
            //이후에 SecurityContextHolder를 이용해서 Authentication 객체를 꺼내서 사용할 수 있다.
            SecurityContext context = SecurityContextHolder.getContext();
            context.setAuthentication(authenticationToken);

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            handleException(response, e);
        }
    }

    private void handleException(HttpServletResponse response, Exception e) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        response.getWriter().println("{\"error\": \"" + e.getMessage() + "\"}");
    }
}
