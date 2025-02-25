package projects.blog.post.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostDto {
    private Long id;
    private String title;
    private String content;
    private Long memberId;
    private Long categoryId;

    @QueryProjection
    public PostDto(Long id, String title) {
        this.id = id;
        this.title = title;
    }
}
