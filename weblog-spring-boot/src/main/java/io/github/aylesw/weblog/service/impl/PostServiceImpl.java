package io.github.aylesw.weblog.service.impl;

import io.github.aylesw.weblog.dto.CommentDto;
import io.github.aylesw.weblog.dto.PostDto;
import io.github.aylesw.weblog.entity.Post;
import io.github.aylesw.weblog.repository.PostRepository;
import io.github.aylesw.weblog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final ModelMapper mapper;

    @Override
    public List<PostDto> getPosts() {
        List<Post> posts = postRepository.findAllByOrderByCreatedDesc();
        return posts.stream().map(this::mapToDto).toList();
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        postDto.setId(null);
        postDto.setCreated(new Date());
        Post post = postRepository.save(mapper.map(postDto, Post.class));
        return mapToDto(post);
    }

    private PostDto mapToDto(Post post) {
        PostDto postDto = mapper.map(post, PostDto.class);
        postDto.setComments(post.getComments().stream().map(comment -> {
            CommentDto commentDto = mapper.map(comment, CommentDto.class);
            commentDto.setReplies(comment.getReplies().stream().map(reply -> mapper.map(reply, CommentDto.class)).toList());
            return commentDto;
        }).toList());
        return postDto;
    }
}
