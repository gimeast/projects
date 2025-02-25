package projects.blog.member.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberVo {
    private Long id;
    private String email;
    private String name;
}
