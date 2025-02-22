package projects.blog.menu.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import projects.blog.menu.entity.Menu;

import java.util.List;

@SpringBootTest
public class MenuRepositoryTest {

    @Autowired
    private MenuRepository menuRepository;

    @Test
    @DisplayName("메뉴목록 list로 불러오기")
    void getMenus() {
        List<Menu> menus = menuRepository.findAllWithChildren();
        for (Menu menu : menus) {
            System.out.println("menu = " + menu);
        }
    }
}
