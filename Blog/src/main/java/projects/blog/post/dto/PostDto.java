package projects.blog.post.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PostDto {
    private Long id;
    private String title;
    private String content;
    private Long memberId;
    private Long categoryId;
    private LocalDateTime regDate;

    @QueryProjection
    public PostDto(Long id, String title, LocalDateTime regDate) {
        this.id = id;
        this.title = title;
        this.regDate = regDate;
    }

    public PostDto(Long id, String title, String content, Long memberId, Long categoryId, LocalDateTime regDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.memberId = memberId;
        this.categoryId = categoryId;
        this.regDate = regDate;
    }
}
