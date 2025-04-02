package gimeast.vehiclemanagement.member.dto;

import gimeast.vehiclemanagement.member.entity.MemberEntity;
import gimeast.vehiclemanagement.member.entity.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MemberDTO {
    private Long idx;
    private String mid;
    private String mpw;
    private String name;
    private String email;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
    private Set<MemberRole> roleSet;

    public Map<String, Object> getDataMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("idx", idx);
        map.put("mid", mid);
        map.put("name", name);
        map.put("email", email);
        map.put("roleSet", roleSet);
        return map;
    }

    public MemberDTO(MemberEntity memberEntity) {
        this.idx = memberEntity.getIdx();
        this.mid = memberEntity.getMid();
        this.mpw = memberEntity.getMpw();
        this.name = memberEntity.getName();
        this.email = memberEntity.getEmail();
        this.regDate = memberEntity.getRegDate();
        this.modDate = memberEntity.getModDate();
        this.roleSet = memberEntity.getRoleSet();
    }
}
