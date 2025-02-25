package projects.blog.post.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import projects.blog.category.repository.CategoryRepository;
import projects.blog.member.repository.MemberRepository;
import projects.blog.menu.entity.Menu;
import projects.blog.menu.repository.MenuRepository;
import projects.blog.post.dto.PostDto;
import projects.blog.post.entity.Post;
import projects.blog.post.repository.PostRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;
    private final MenuRepository menuRepository;

    public List<PostDto> getPosts(Long menuId) {
        Optional<Menu> byId = menuRepository.findById(menuId);
        if(byId.isPresent()) {
            Menu menu = byId.get();

            if(ObjectUtils.isEmpty(menu.getParent())) {
                log.info("대메뉴 선택");
                return postRepository.findAllByChildMenuId(menuId);
            } else {
                log.info("소메뉴 선택");
                return postRepository.findAllByParentMenuId(menuId);
            }
        }

        return null;
    }

    @Transactional
    public Long savePost(PostDto postDto) {
        Post post = dtoToEntity(postDto);
        Post save = postRepository.save(post);
        return save.getId();
    }

    public Post dtoToEntity(PostDto postDto) {
        return Post.builder()
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .member(memberRepository.getReferenceById(postDto.getMemberId()))
                .category(categoryRepository.getReferenceById(postDto.getCategoryId()))
                .build();

    }
}
