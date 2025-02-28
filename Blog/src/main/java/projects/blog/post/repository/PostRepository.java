package projects.blog.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import projects.blog.post.dto.PostDto;
import projects.blog.post.entity.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    /**
     * 소메뉴에 포함된 게시물 전체 조회
     * @param menuId
     * @return
     */
    @Query("select new projects.blog.post.dto.PostDto(p.id, p.title, p.regDate) " +
            "from Post p " +
            "left join p.category " +
            "where p.category.menu.id = :menuId")
    List<PostDto> findAllPostsByMenuId(Long menuId);

    /**
     * 대메뉴 하위 전체 게시물 조회
     * @param menuId
     * @return
     */
    @Query("select new projects.blog.post.dto.PostDto(p.id, p.title, p.regDate) " +
            "from Post p " +
            "left join p.category " +
            "where p.category.menu.parent.id = :menuId")
    List<PostDto> findAllPostsByParentMenuId(Long menuId);
}
