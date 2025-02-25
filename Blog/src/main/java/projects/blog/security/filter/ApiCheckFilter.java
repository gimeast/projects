package projects.blog.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import net.minidev.json.JSONObject;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import projects.blog.security.util.JWTUtil;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;

@Log4j2
public class ApiCheckFilter extends OncePerRequestFilter {

    private final AntPathMatcher antPathMatcher;
    private final String pattern;
    private final JWTUtil jwtUtil;

    public ApiCheckFilter(String pattern, JWTUtil jwtUtil) {
        this.antPathMatcher = new AntPathMatcher();
        this.pattern = pattern;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String requestURI = request.getRequestURI();

        // 로그인 엔드포인트는 검증에서 제외
        if (requestURI.equals("/api/v1/user/login") || requestURI.startsWith("/api/public") || requestURI.startsWith("/api/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        if (antPathMatcher.match(pattern, requestURI)) {
            boolean checkHeader = checkAuthHeader(request);

            if (checkHeader) {
                filterChain.doFilter(request, response);
                return;
            } else {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setContentType("application/json;charset=utf-8");

                JSONObject json = new JSONObject();
                String message = "FAIL CHECK API TOKEN";
                json.put("code", "403");
                json.put("message", message);

                PrintWriter out = response.getWriter();
                out.print(json);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private boolean checkAuthHeader(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");

        if (StringUtils.hasText(authorization) && authorization.startsWith("Bearer ")) {
            try {
                String email = jwtUtil.validateAndExtract(authorization.substring(7));
                if (email != null && !email.isEmpty()) {
                    // SecurityContext에 Authentication 설정
                    UsernamePasswordAuthenticationToken auth =
                            new UsernamePasswordAuthenticationToken(email, null, Collections.emptyList());
                    SecurityContextHolder.getContext().setAuthentication(auth);
                    return true;
                }
            } catch (Exception e) {
                log.error("checkAuthHeader error: {}", e.getMessage());
            }
        }

        return false;
    }

}

