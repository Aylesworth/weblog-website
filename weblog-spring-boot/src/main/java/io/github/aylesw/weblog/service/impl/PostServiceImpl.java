package io.github.aylesw.weblog.service.impl;

import io.github.aylesw.weblog.dto.PostDto;
import io.github.aylesw.weblog.entity.Post;
import io.github.aylesw.weblog.repository.PostRepository;
import io.github.aylesw.weblog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final ModelMapper mapper;
    @Override
    public List<PostDto> getPosts() {
        List<Post> posts = postRepository.findAllOrderByCreatedDesc();
        return posts.stream().map(post -> mapper.map(post, PostDto.class)).toList();
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        postDto.setId(null);
        Post post = postRepository.save(mapper.map(postDto, Post.class));
        return mapper.map(post, PostDto.class);
    }
}
