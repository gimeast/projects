package projects.blog.post.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import projects.blog.post.dto.PostDto;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostServiceTest {

    @Autowired
    PostService postService;

    @Test
    void savePost() {
        PostDto postDto = new PostDto();
        postDto.setId(25L);
        postDto.setTitle("수정된 test 입니당");
        postDto.setContent("<p>수정해따오기</p>");
        postDto.setMemberId(2L);
        postDto.setCategoryId(3L);

        postService.savePost(postDto);
    }
}