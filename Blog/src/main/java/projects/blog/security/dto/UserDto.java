package projects.blog.security.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String email;
    private String name;
    private boolean fromSocial;

    @QueryProjection
    public UserDto(Long id, String email, String name, boolean fromSocial) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.fromSocial = fromSocial;
    }
}
