package io.github.aylesw.weblog.service;

import io.github.aylesw.weblog.dto.CommentDto;

import java.util.List;

public interface CommentService {
    List<CommentDto> getComments(String postId);
    CommentDto addComment(String postId, CommentDto commentDto);
}
