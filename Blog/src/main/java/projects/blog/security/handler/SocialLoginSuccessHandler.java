package projects.blog.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import projects.blog.security.dto.AuthMemberDto;

import java.io.IOException;

@Slf4j
public class SocialLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    private final PasswordEncoder passwordEncoder;

    @Value("${gimeast.temp-password.value}")
    private String TEMP_PASSWORD;

    public SocialLoginSuccessHandler(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        AuthMemberDto authMemberDto = (AuthMemberDto) authentication.getPrincipal();

        boolean fromSocial = authMemberDto.isFromSocial();

        boolean isPasswordMatching = passwordEncoder.matches(TEMP_PASSWORD, authMemberDto.getPassword());

        if (fromSocial && isPasswordMatching) {
            redirectStrategy.sendRedirect(request, response, "/member/modify?from=social");
        }
    }
}
