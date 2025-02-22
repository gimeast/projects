package projects.blog.menu.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import projects.blog.menu.dto.MenuDto;
import projects.blog.menu.entity.Menu;
import projects.blog.menu.repository.MenuRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;

    @Transactional
    public List<MenuDto> getMenuDtoList() {
        List<Menu> rootParentMenus = menuRepository.findAllWithChildren();
        List<MenuDto> menus = rootParentMenus.stream().map(MenuDto::of).toList();
        return menus;
    }
}
