package gimeast.vehiclemanagement.member.service;

import gimeast.vehiclemanagement.member.dto.MemberDTO;
import gimeast.vehiclemanagement.member.entity.MemberEntity;
import gimeast.vehiclemanagement.member.entity.MemberRole;
import gimeast.vehiclemanagement.member.exception.MemberExceptions;
import gimeast.vehiclemanagement.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Log4j2
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberDTO read(String mid, String mpw) {
        Optional<MemberEntity> result = memberRepository.findByMid(mid);
        MemberEntity memberEntity = result.orElseThrow(MemberExceptions.BAD_CREDENTIALS::get);

        String decodedPassword = decodeBase64Password(mpw);
        if (!passwordEncoder.matches(decodedPassword, memberEntity.getMpw())) {
            throw MemberExceptions.BAD_CREDENTIALS.get();
        }
        return new MemberDTO(memberEntity);
    }

    public MemberDTO getByMid(String mid) {
        Optional<MemberEntity> result = memberRepository.findByMid(mid);
        MemberEntity memberEntity = result.orElseThrow(MemberExceptions.NOT_FOUND::get);
        return new MemberDTO(memberEntity);
    }

    public Long join(MemberDTO memberDTO) {
        Set<MemberRole> roleSet = new HashSet<>();
        roleSet.add(MemberRole.USER);

        String decodedPassword = decodeBase64Password(memberDTO.getMpw());

        MemberEntity newMember = MemberEntity.builder()
                .mid(memberDTO.getMid())
                .mpw(passwordEncoder.encode(decodedPassword))
                .name(memberDTO.getName())
                .email(memberDTO.getEmail())
                .roleSet(roleSet)
                .build();

        try {
            memberRepository.save(newMember);
        } catch (DataIntegrityViolationException e) {
            throw MemberExceptions.DUPLICATE.get();
        }

        return newMember.getIdx();
    }

    private String decodeBase64Password(String encodedPassword) {
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(encodedPassword);
            return new String(decodedBytes);
        } catch (IllegalArgumentException e) {
            // Base64 형식이 아닌 경우 예외 처리
            log.error("비밀번호가 유효한 Base64 형식이 아닙니다: {}", e.getMessage());
            throw MemberExceptions.INVALID.get();
        }
    }

}
