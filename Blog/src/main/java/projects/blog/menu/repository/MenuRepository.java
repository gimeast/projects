package projects.blog.menu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import projects.blog.menu.entity.Menu;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    @Query("SELECT DISTINCT m FROM Menu m LEFT JOIN FETCH m.children WHERE m.parent IS NULL AND m.enabled = true")
    List<Menu> findAllWithChildren();

}
