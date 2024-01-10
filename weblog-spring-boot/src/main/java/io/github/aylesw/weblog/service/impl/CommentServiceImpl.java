package io.github.aylesw.weblog.service.impl;

import io.github.aylesw.weblog.dto.CommentDto;
import io.github.aylesw.weblog.entity.Comment;
import io.github.aylesw.weblog.entity.Post;
import io.github.aylesw.weblog.exception.BlogApiException;
import io.github.aylesw.weblog.exception.ResourceNotFoundException;
import io.github.aylesw.weblog.repository.CommentRepository;
import io.github.aylesw.weblog.repository.PostRepository;
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

        Comment comment = mapper.map(commentDto, Comment.class);
        comment.setId(null);
        comment.setPosted(new Date());
        comment.setLikedEmails(new ArrayList<>());
        comment.setReplies(new ArrayList<>());
        comment = commentRepository.save(comment);

        post.getComments().add(comment);
        postRepository.save(post);

        return mapToDto(comment);
    }

    @Override
    public Integer likeComment(String id, String email) {
        if (email == null || email.isEmpty())
            throw new BlogApiException("'email' field is required in the request body", HttpStatus.BAD_REQUEST);

        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", id));

        comment.getLikedEmails().add(email);
        comment = commentRepository.save(comment);

        return comment.getLikedEmails().size();
    }

    @Override
    public Integer unlikeComment(String id, String email) {
        if (email == null || email.isEmpty())
            throw new BlogApiException("'email' field is required in the request body", HttpStatus.BAD_REQUEST);

        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", id));

        comment.getLikedEmails().remove(email);
        comment = commentRepository.save(comment);

        return comment.getLikedEmails().size();
    }

    private CommentDto mapToDto(Comment comment) {
        CommentDto commentDto = CommentDto.builder()
                .id(comment.getId())
                .email(comment.getEmail())
                .content(comment.getContent())
                .likes(comment.getLikedEmails().size())
                .liked(false)
                .build();
        if (comment.getReplies() != null && comment.getReplies().size() > 0) {
            commentDto.setReplies(comment.getReplies().stream().map(this::mapToDto).toList());
        } else {
            commentDto.setReplies(new ArrayList<>());
        }
        return commentDto;
    }
}
