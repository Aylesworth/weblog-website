package io.github.aylesw.weblog.service.impl;

import io.github.aylesw.weblog.dto.CommentDto;
import io.github.aylesw.weblog.dto.PostDto;
import io.github.aylesw.weblog.entity.Post;
import io.github.aylesw.weblog.exception.ResourceNotFoundException;
import io.github.aylesw.weblog.repository.PostRepository;
import io.github.aylesw.weblog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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
    public PostDto createPost(PostDto postDto) {
        postDto.setId(null);
        postDto.setCreated(new Date());
        postDto.setUpdated(postDto.getCreated());
        Post post = postRepository.save(mapper.map(postDto, Post.class));
        return mapToDto(post);
    }

    @Override
    public List<PostDto> getPosts() {
        List<Post> posts = postRepository.findAllByOrderByCreatedDesc();
        return posts.stream().map(post -> {
            PostDto postDto = mapToDto(post);
            postDto.setContent(getContentShort(post.getContent()));
            postDto.setThumbnail(getThumbnail(post.getContent()));
            return postDto;
        }).toList();
    }

    @Override
    public PostDto getPost(String id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        PostDto postDto = mapToDto(post);
        postDto.setThumbnail(getThumbnail(post.getContent()));
        postDto.setContent(adjustImages(post.getContent()));
        return postDto;
    }

    private PostDto mapToDto(Post post) {
        return mapper.map(post, PostDto.class);
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

    private String adjustImages(String content) {
        Document doc = Jsoup.parse(content);
        Elements images = doc.select("img");
        images.forEach(image -> {
            image.attr("width", "100%")
                    .attr("height", "auto");
        });
        return doc.html();
    }
}
