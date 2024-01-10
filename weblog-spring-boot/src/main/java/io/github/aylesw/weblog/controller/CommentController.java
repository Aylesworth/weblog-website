package io.github.aylesw.weblog.controller;

import io.github.aylesw.weblog.dto.CommentDto;
import io.github.aylesw.weblog.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<CommentDto>> getComments(
            @PathVariable String postId,
            @RequestParam(name = "email", required = false) String email) {
        return ResponseEntity.ok(commentService.getComments(postId, email));
    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> addComment(@PathVariable String postId, @Valid @RequestBody CommentDto commentDto) {
        return ResponseEntity.ok(commentService.addComment(postId, commentDto));
    }

    @PostMapping("/comments/{id}/like")
    public ResponseEntity<Integer> likeComment(@PathVariable String id, @RequestBody Map<String, Object> body) {
        return ResponseEntity.ok(commentService.likeComment(id, (String) body.get("email")));
    }

    @PostMapping("/comments/{id}/unlike")
    public ResponseEntity<Integer> unlikeComment(@PathVariable String id, @RequestBody Map<String, Object> body) {
        return ResponseEntity.ok(commentService.unlikeComment(id, (String) body.get("email")));
    }

    @PostMapping("/comments/{id}/replies")
    public ResponseEntity<CommentDto> replyComment(@PathVariable String id, @Valid @RequestBody CommentDto replyDto) {
        return ResponseEntity.ok(commentService.replyComment(id, replyDto));
    }

    @GetMapping("/comments/{id}/replies")
    public ResponseEntity<List<CommentDto>> getReplies(
            @PathVariable String id,
            @RequestParam(name = "email", required = false) String email) {
        return ResponseEntity.ok(commentService.getReplies(id, email));
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<CommentDto> deleteComment(@PathVariable String id) {
        return ResponseEntity.ok(commentService.deleteComment(id));
    }
}
