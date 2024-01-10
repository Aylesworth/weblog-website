package io.github.aylesw.weblog.service.impl;

import io.github.aylesw.weblog.dto.CommentDto;
import io.github.aylesw.weblog.entity.Comment;
import io.github.aylesw.weblog.entity.Post;
import io.github.aylesw.weblog.exception.ResourceNotFoundException;
import io.github.aylesw.weblog.repository.CommentRepository;
import io.github.aylesw.weblog.repository.PostRepository;
import io.github.aylesw.weblog.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
    public List<CommentDto> getComments(String postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        return post.getComments().stream()
                .map(comment -> mapper.map(comment, CommentDto.class))
                .toList();
    }

    @Override
    public CommentDto addComment(String postId, CommentDto commentDto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        commentDto.setId(null);
        commentDto.setPosted(new Date());
        commentDto.setLikes(0);
        commentDto.setReplies(new ArrayList<>());

        Comment comment = mapper.map(commentDto, Comment.class);
        comment = commentRepository.save(comment);

        post.getComments().add(comment);
        postRepository.save(post);

        return mapper.map(comment, CommentDto.class);
    }
}
