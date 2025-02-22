package projects.blog.menu.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import projects.blog.menu.dto.MenuDto;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MenuServiceTest {

    @Autowired
    private MenuService menuService;

    @Test
    @DisplayName("메뉴목록 list 불러오기")
    void getMenuDtoList() {
        List<MenuDto> menuDtoList = menuService.getMenuDtoList();
        System.out.println("menuDtoList = " + menuDtoList);
    }
}