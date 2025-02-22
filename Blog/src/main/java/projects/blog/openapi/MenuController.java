package projects.blog.openapi;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import projects.blog.menu.dto.MenuDto;
import projects.blog.menu.service.MenuService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/public")
public class MenuController {

    private final MenuService menuService;

    @GetMapping("/menus")
    public ResponseEntity<List<MenuDto>> getMenus() {
        List<MenuDto> menuDtoList = menuService.getMenuDtoList();
        return ResponseEntity.ok(menuDtoList);
    }
}
