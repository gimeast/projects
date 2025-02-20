package projects.blog.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import projects.blog.member.entity.Member;
import projects.blog.member.entity.MemberRole;
import projects.blog.member.repository.MemberRepository;
import projects.blog.security.dto.AuthMemberDto;

import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class OAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${gimeast.temp-password.value}")
    private String TEMP_PASSWORD;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String email = null;
        String clientName = userRequest.getClientRegistration().getClientName();

        if (clientName.equals("Google")) {
            email = oAuth2User.getAttribute("email");
        }

        Member member = findOrSaveSocialMember(email);

        AuthMemberDto authMemberDto = new AuthMemberDto(
                member.getEmail(),
                member.getPassword(),
                true,
                member.getRoleSet().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                        .collect(Collectors.toList()),
                oAuth2User.getAttributes()
        );

        authMemberDto.setName(member.getName());

        return authMemberDto;
    }

    private Member findOrSaveSocialMember(String email) {
        Optional<Member> byEmail = memberRepository.findByEmailAndSocial(email, true);

        if (byEmail.isPresent()) {
            return byEmail.get();
        } else {
            Member member = Member.builder()
                    .email(email)
                    .name(email)
                    .password(passwordEncoder.encode(TEMP_PASSWORD))
                    .fromSocial(true)
                    .build();

            member.addMemberRole(MemberRole.USER);

            memberRepository.save(member);

            return member;
        }
    }

}
