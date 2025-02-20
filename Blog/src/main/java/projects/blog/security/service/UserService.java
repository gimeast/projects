package projects.blog.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import projects.blog.member.entity.Member;
import projects.blog.member.repository.MemberRepository;
import projects.blog.security.dto.AuthMemberDto;

import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("UserService loadUserByUsername {}", username);

        Optional<Member> byEmail = memberRepository.findByEmailAndSocial(username, false);

        if(byEmail.isEmpty()) {
            throw new UsernameNotFoundException("Check Email or Social");
        }

        Member member = byEmail.get();

        AuthMemberDto authMemberDto = new AuthMemberDto(
                member.getEmail(),
                member.getPassword(),
                member.isFromSocial(),
                member.getRoleSet().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.name())).collect(Collectors.toSet())
        );

        authMemberDto.setName(member.getName());
        authMemberDto.setFromSocial(member.isFromSocial());

        log.info("authMemberDto: {}", authMemberDto);
        return authMemberDto;
    }

}
