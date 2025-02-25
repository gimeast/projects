package projects.blog.menu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import projects.blog.menu.entity.Menu;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    @Query("select distinct m from Menu m left join fetch m.children where m.parent is null and m.enabled = true")
    List<Menu> findAllWithChildren();

}
