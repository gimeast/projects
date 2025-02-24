package projects.blog.menu.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import projects.blog.menu.entity.Menu;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MenuDto {
    private Long id;
    private String title;
    private List<MenuDto> children;
    private int depth;
    private int sort;

    public static MenuDto of(Menu menu) {
        return new MenuDto(menu.getId(), menu.getTitle(), menu.getChildren().stream().map(MenuDto::of).toList(), menu.getDepth(), menu.getSort());
    }
}
