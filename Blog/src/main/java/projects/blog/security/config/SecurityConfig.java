package projects.blog.security.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;
import projects.blog.security.filter.ApiCheckFilter;
import projects.blog.security.filter.ApiLoginFilter;
import projects.blog.security.handler.ApiLoginFailHandler;
import projects.blog.security.handler.SocialLoginSuccessHandler;
import projects.blog.security.repository.RefreshTokenRepository;
import projects.blog.security.util.JWTUtil;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@Log4j2
@RequiredArgsConstructor
public class SecurityConfig {

    private final RefreshTokenRepository refreshTokenRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth-> {
            auth.requestMatchers("/api/v1/user/login").permitAll();
            auth.requestMatchers("/api/public/**").permitAll();
            auth.requestMatchers("/api/auth/**").permitAll();
            auth.requestMatchers("/api/**").authenticated();
            auth.anyRequest().authenticated();
        });

        http.addFilterBefore(apiCheckFilter(), UsernamePasswordAuthenticationFilter.class);
        http.formLogin(AbstractHttpConfigurer::disable);
        http.logout(Customizer.withDefaults());
        http.csrf(AbstractHttpConfigurer::disable);
//        http.oauth2Login(login -> {
//            login.successHandler(customSuccessHandler());
//        });
        http.rememberMe(rememberMe -> {
            rememberMe.tokenValiditySeconds(60 * 60 * 24 * 7);
        });

        AuthenticationManagerBuilder sharedObject = http.getSharedObject(AuthenticationManagerBuilder.class);
        AuthenticationManager authManager = sharedObject.build();

        http.authenticationManager(authManager);

        ApiLoginFilter apiLoginFilter = getApiLoginFilter(authManager);
        http.addFilterBefore(apiLoginFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    private ApiLoginFilter getApiLoginFilter(AuthenticationManager authManager) {
        ApiLoginFilter apiLoginFilter = new ApiLoginFilter("/api/v1/user/login", jwtUtil(), refreshTokenRepository);
        apiLoginFilter.setAuthenticationManager(authManager);
        apiLoginFilter.setAuthenticationFailureHandler(new ApiLoginFailHandler());
        return apiLoginFilter;
    }

    @Bean
    public JWTUtil jwtUtil() {
        return new JWTUtil();
    }

    @Bean
    public AuthenticationSuccessHandler customSuccessHandler() {
        return new SocialLoginSuccessHandler(passwordEncoder());
    }

    @Bean
    public OncePerRequestFilter apiCheckFilter() {
        return new ApiCheckFilter("/api/v1/**", jwtUtil());
    }

}
