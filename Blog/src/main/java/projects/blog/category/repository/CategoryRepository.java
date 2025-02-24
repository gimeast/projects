package projects.blog.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import projects.blog.category.dto.CategoryDto;
import projects.blog.category.entity.Category;
import projects.blog.menu.entity.Menu;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("select new projects.blog.category.dto.CategoryDto(c.id, c.name) from Category c where c.menu.id = :menuId")
    List<CategoryDto> findCategoriesBy(Long menuId);

    Long menu(Menu menu);
}
