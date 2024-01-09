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
            PostDto postDto = PostDto.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .author(post.getAuthor())
                    .created(post.getCreated())
                    .build();

            Document doc = Jsoup.parse(post.getContent());
            String contentShort = doc.text();
            if (contentShort.length() > 100)
                contentShort = contentShort.substring(0, 100) + "...";
            postDto.setContent(contentShort);

            Element firstImage = doc.selectFirst("img");
            if (firstImage != null) {
                firstImage.attr("width", "100%")
                        .attr("height", "auto")
                        .attr("style", "object-fit: cover;");
                postDto.setThumbnail(firstImage.outerHtml());
            } else {
                postDto.setThumbnail("<img class=\"card-img-top\" src=\"http://placehold.it/978x300\" alt=\"\">");
            }
            return postDto;
        }).toList();
    }

    @Override
    public PostDto getPost(String id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        return mapToDto(post);
    }

    private PostDto mapToDto(Post post) {
        return mapper.map(post, PostDto.class);
    }
}
