package projects.blog.post.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import projects.blog.category.repository.CategoryRepository;
import projects.blog.member.repository.MemberRepository;
import projects.blog.post.dto.PostDto;
import projects.blog.post.entity.Post;
import projects.blog.post.repository.PostRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;

    public List<PostDto> getPosts(Long menuId) {
        return postRepository.findAllByMenuId(menuId);
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
