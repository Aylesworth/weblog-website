package io.github.aylesw.weblog.service;

import io.github.aylesw.weblog.dto.CommentDto;

import java.util.List;

public interface CommentService {
    List<CommentDto> getComments(String postId, String email);
    CommentDto addComment(String postId, CommentDto commentDto);
    Integer likeComment(String commentId, String email);
    Integer unlikeComment(String commentId, String email);
    CommentDto replyComment(String commentId, CommentDto replyDto);
    List<CommentDto> getReplies(String commentId, String email);
    CommentDto deleteComment(String commentId);
}
