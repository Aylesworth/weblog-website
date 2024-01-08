package io.github.aylesw.weblog.service;

import io.github.aylesw.weblog.dto.PostDto;

import java.util.List;

public interface PostService {
    List<PostDto> getPosts();
    PostDto createPost(PostDto postDto);
}
