package io.github.aylesw.weblog.service;

import io.github.aylesw.weblog.dto.CommentDto;

import java.util.List;

public interface CommentService {
    List<CommentDto> getComments(String postId, String email);
    CommentDto addComment(String postId, CommentDto commentDto);
    Integer likeComment(String id, String email);
    Integer unlikeComment(String id, String email);
}
