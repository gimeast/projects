package projects.blog.security.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import projects.blog.member.repository.MemberRepository;
import projects.blog.security.dto.UserDto;
import projects.blog.security.util.JWTUtil;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
@Log4j2
public class UserController {

    private final JWTUtil jwtUtil;
    private final MemberRepository memberRepository;

    @GetMapping("/me")
    public ResponseEntity<UserDto> getUser(@RequestHeader("Authorization") String token) {
        try {
            String email = jwtUtil.validateAndExtract(token);
            Optional<UserDto> byEmail = memberRepository.findUserDtoByEmail(email);

            return byEmail.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.badRequest().build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



}
