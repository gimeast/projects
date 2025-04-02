package gimeast.vehiclemanagement.member.controller;

import gimeast.vehiclemanagement.member.dto.MemberDTO;
import gimeast.vehiclemanagement.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
@Log4j2
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/join")
    public ResponseEntity<Long> join(@RequestBody MemberDTO memberDTO) {
        Long idx = memberService.join(memberDTO);
        return ResponseEntity.ok(idx);
    }
}
