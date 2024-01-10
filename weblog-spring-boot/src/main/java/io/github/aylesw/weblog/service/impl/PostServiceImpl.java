package io.github.aylesw.weblog.service.impl;

import io.github.aylesw.weblog.dto.PostDto;
import io.github.aylesw.weblog.dto.UserDto;
import io.github.aylesw.weblog.entity.Post;
import io.github.aylesw.weblog.entity.User;
import io.github.aylesw.weblog.exception.ResourceNotFoundException;
import io.github.aylesw.weblog.repository.CommentRepository;
import io.github.aylesw.weblog.repository.PostRepository;
import io.github.aylesw.weblog.repository.UserRepository;
import io.github.aylesw.weblog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ModelMapper mapper;

    @Override
    public PostDto createPost(PostDto postDto) {
        User user = userRepository.findByEmail(postDto.getAuthor())
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", postDto.getAuthor()));
        Post post = mapper.map(postDto, Post.class);
        post.setId(null);
        post.setAuthorDetails(user);
        post.setCreated(new Date());
        post.setUpdated(null);
        post.setComments(new ArrayList<>());
        post = postRepository.save(post);
        return mapToDto(post);
    }

    @Override
    public List<PostDto> getPosts() {
        List<Post> posts = postRepository.findAllByOrderByCreatedDesc();
        return posts.stream().map(post -> {
            PostDto postDto = mapToDto(post);
            postDto.setContent(getContentShort(post.getContent()));
            return postDto;
        }).toList();
    }

    @Override
    public PostDto getPost(String id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        return mapToDto(post);
    }

    @Override
    public PostDto updatePost(String id, PostDto postDto) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setUpdated(new Date());
        post = postRepository.save(post);
        return mapToDto(post);
    }

    @Override
    public PostDto deletePost(String id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        post.getComments().forEach(comment -> {
            comment.getReplies().forEach(commentRepository::delete);
            commentRepository.delete(comment);
        });
        postRepository.delete(post);
        return mapToDto(post);
    }

    @Override
    public List<PostDto> searchForPosts(String keyword) {
        List<Post> posts = postRepository.findByTitleOrContentContaining(keyword);
        posts = posts.stream().filter(post ->
                post.getTitle().toLowerCase().contains(keyword.toLowerCase())
                        || Jsoup.parse(post.getContent()).text().toLowerCase().contains(keyword.toLowerCase())
        ).toList();
        return posts.stream().map(post -> {
            PostDto postDto = mapToDto(post);
            postDto.setContent(getContentShort(post.getContent()));
            return postDto;
        }).toList();
    }

    @Override
    public List<PostDto> getPostsByAuthor(String email) {
        List<Post> posts = postRepository.findByAuthorOrderByCreatedDesc(email);
        return posts.stream().map(post -> {
            PostDto postDto = mapToDto(post);
            postDto.setContent(getContentShort(post.getContent()));
            return postDto;
        }).toList();
    }

    private PostDto mapToDto(Post post) {
        PostDto postDto = mapper.map(post, PostDto.class);
        postDto.setAuthorDetails(mapper.map(post.getAuthorDetails(), UserDto.class));
        postDto.setThumbnail(getThumbnail(post.getContent()));
        return postDto;
    }

    private String getContentShort(String content) {
        Document doc = Jsoup.parse(content);
        String contentShort = doc.text();
        if (contentShort.length() > 400)
            contentShort = contentShort.substring(0, 400) + "...";
        return contentShort;
    }

    private String getThumbnail(String content) {
        Document doc = Jsoup.parse(content);
        Element firstImage = doc.selectFirst("img");
        if (firstImage != null) {
            firstImage.attr("width", "100%")
                    .attr("height", "auto")
                    .attr("style", "object-fit: cover;");
            return firstImage.outerHtml();
        }
        return null;
    }
}
