package projects.blog.category.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import projects.blog.category.dto.CategoryDto;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryRepositoryTest {

    @Autowired
    CategoryRepository categoryRepository;

    @Test
    @DisplayName("해당 메뉴 카테고리 전체 조회")
    void getCategoriesByMenuId() {
        //given
        Long menuId = 13L;

        //when
        List<CategoryDto> list = categoryRepository.findCategoriesBy(menuId);

        //then
        System.out.println("list = " + list);
    }
}