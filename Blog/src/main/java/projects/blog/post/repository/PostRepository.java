package projects.blog.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import projects.blog.post.dto.PostDto;
import projects.blog.post.entity.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select new projects.blog.post.dto.PostDto(p.id, p.title, p.regDate) from Post p left join p.category where p.category.menu.id = :menuId")
    List<PostDto> findAllByParentMenuId(Long menuId);

    @Query("select new projects.blog.post.dto.PostDto(p.id, p.title, p.regDate) " +
            "from Post p " +
            "left join p.category " +
            "where p.category.menu.parent.id = :menuId")
    List<PostDto> findAllByChildMenuId(Long menuId);
}
