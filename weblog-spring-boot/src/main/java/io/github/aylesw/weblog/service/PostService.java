package io.github.aylesw.weblog.service;

import io.github.aylesw.weblog.dto.PostDto;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);
    List<PostDto> getPosts();
    PostDto getPost(String id);
    PostDto updatePost(String id, PostDto postDto);
    PostDto deletePost(String id);
    List<PostDto> searchForPosts(String keyword);
    List<PostDto> getPostsByAuthor(String email);
}
