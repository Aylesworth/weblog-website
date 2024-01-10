package io.github.aylesw.weblog.controller;

import io.github.aylesw.weblog.dto.CommentDto;
import io.github.aylesw.weblog.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<CommentDto>> getComments(@PathVariable String postId) {
        return ResponseEntity.ok(commentService.getComments(postId));
    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> addComment(@PathVariable String postId, @RequestBody CommentDto commentDto) {
        return ResponseEntity.ok(commentService.addComment(postId, commentDto));
    }
}
