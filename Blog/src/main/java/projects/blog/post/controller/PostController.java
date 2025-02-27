package projects.blog.post.controller;

import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import projects.blog.post.dto.PostDto;
import projects.blog.post.service.PostService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
@Log4j2
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<Long> savePost(@RequestBody PostDto postDto) {
        Long postId = postService.savePost(postDto);
        return ResponseEntity.ok(postId);
    }

    @GetMapping("/menus/{menuId}")
    public ResponseEntity<List<PostDto>> getPosts(@PathVariable Long menuId) {
        List<PostDto> posts = postService.getPosts(menuId);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPost(@PathVariable Long postId) {
        PostDto post = postService.getPost(postId);
        log.info("post========>{}", post);
        return ResponseEntity.ok(post);
    }
}
