package io.github.aylesw.weblog.service.impl;

import io.github.aylesw.weblog.dto.CommentDto;
import io.github.aylesw.weblog.dto.UserDto;
import io.github.aylesw.weblog.entity.Comment;
import io.github.aylesw.weblog.entity.Post;
import io.github.aylesw.weblog.entity.User;
import io.github.aylesw.weblog.exception.BlogApiException;
import io.github.aylesw.weblog.exception.ResourceNotFoundException;
import io.github.aylesw.weblog.repository.CommentRepository;
import io.github.aylesw.weblog.repository.PostRepository;
import io.github.aylesw.weblog.repository.UserRepository;
import io.github.aylesw.weblog.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ModelMapper mapper;

    @Override
    public List<CommentDto> getComments(String postId, String email) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        return post.getComments().stream()
                .map(comment -> {
                    CommentDto commentDto = mapToDto(comment);
                    commentDto.setLiked(email != null && !email.isEmpty() && comment.getLikedEmails().contains(email));
                    return commentDto;
                }).toList();
    }

    @Override
    public CommentDto addComment(String postId, CommentDto commentDto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        User user = userRepository.findByEmail(commentDto.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", commentDto.getEmail()));

        Comment comment = mapper.map(commentDto, Comment.class);
        comment.setId(null);
        comment.setUser(user);
        comment.setPosted(new Date());
        comment.setLikedEmails(new ArrayList<>());
        comment.setReplies(new ArrayList<>());
        comment = commentRepository.save(comment);

        post.getComments().add(0, comment);
        postRepository.save(post);

        return mapToDto(comment);
    }

    @Override
    public Integer likeComment(String commentId, String email) {
        if (email == null || email.isEmpty())
            throw new BlogApiException("'email' field is required in the request body", HttpStatus.BAD_REQUEST);

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        comment.getLikedEmails().add(email);
        comment = commentRepository.save(comment);

        return comment.getLikedEmails().size();
    }

    @Override
    public Integer unlikeComment(String commentId, String email) {
        if (email == null || email.isEmpty())
            throw new BlogApiException("'email' field is required in the request body", HttpStatus.BAD_REQUEST);

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        comment.getLikedEmails().remove(email);
        comment = commentRepository.save(comment);

        return comment.getLikedEmails().size();
    }

    @Override
    public CommentDto replyComment(String commentId, CommentDto replyDto) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        User user = userRepository.findByEmail(replyDto.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", replyDto.getEmail()));

        Comment reply = mapper.map(replyDto, Comment.class);
        reply.setId(null);
        reply.setUser(user);
        reply.setPosted(new Date());
        reply.setLikedEmails(new ArrayList<>());
        reply.setReplies(new ArrayList<>());
        reply = commentRepository.save(reply);

        comment.getReplies().add(reply);
        commentRepository.save(comment);

        return mapToDto(reply);
    }

    @Override
    public List<CommentDto> getReplies(String commentId, String email) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        return comment.getReplies().stream()
                .map(reply -> {
                    CommentDto replyDto = mapToDto(reply);
                    replyDto.setLiked(email != null && !email.isEmpty() && reply.getLikedEmails().contains(email));
                    return replyDto;
                }).toList();
    }

    @Override
    public CommentDto deleteComment(String commentId) {
        Post post = postRepository.findByCommentsId(commentId);
        if (post != null) {
            var comments = post.getComments().stream().filter(comment -> !comment.getId().equals(commentId)).toList();
            post.setComments(comments);
            postRepository.save(post);
        }

        Comment parent = commentRepository.findByRepliesId(commentId);
        if (parent != null) {
            var replies = parent.getReplies().stream().filter(reply -> !reply.getId().equals(commentId)).toList();
            parent.setReplies(replies);
            commentRepository.save(parent);
        }

        Comment comment = commentRepository.findById(commentId)
                        .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
        commentRepository.delete(comment);

        return mapToDto(comment);
    }

    private CommentDto mapToDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .email(comment.getEmail())
                .user(mapper.map(comment.getUser(), UserDto.class))
                .content(comment.getContent())
                .posted(comment.getPosted())
                .likes(comment.getLikedEmails().size())
                .liked(false)
                .totalReplies(comment.getReplies().size())
                .build();
    }
}
