package io.github.aylesw.weblog.controller;

import io.github.aylesw.weblog.dto.PostDto;
import io.github.aylesw.weblog.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    @GetMapping("/posts")
    public ResponseEntity<List<PostDto>> getPosts(
            @RequestParam(name = "search", required = false) String keyword,
            @RequestParam(name = "email", required = false) String email) {
        if (keyword != null)
            return ResponseEntity.ok(postService.searchForPosts(keyword));
        if (email != null)
            return ResponseEntity.ok(postService.getPostsByAuthor(email));

        return ResponseEntity.ok(postService.getPosts());
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<PostDto> getPost(@PathVariable String id) {
        return ResponseEntity.ok(postService.getPost(id));
    }

    @PostMapping("/posts")
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto) {
        return ResponseEntity.ok(postService.createPost(postDto));
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable String id, @Valid @RequestBody PostDto postDto) {
        return ResponseEntity.ok(postService.updatePost(id, postDto));
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<PostDto> deletePost(@PathVariable String id) {
        return ResponseEntity.ok(postService.deletePost(id));
    }
}
