package com.feeham.social.postfeed.service;

import com.feeham.social.postfeed.model.dto.PostCreateDTO;
import com.feeham.social.postfeed.model.entity.Post;
import com.feeham.social.postfeed.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public List<Post> getPosts(String userEmail) {
        return postRepository.findAll().stream().filter(post -> post.getUserEmail().equals(userEmail)).collect(Collectors.toList());
    }

    @Override
    public List<Post> readPosts(String userEmail) {
        return null;
    }

    @Override
    public boolean createPost(PostCreateDTO postDTO) {
        Post post = new Post();
        post.setTimeCreated(LocalDateTime.now());
        post.setUserEmail(postDTO.getUserEmail());
        post.setContent(postDTO.getContent());
        postRepository.save(post);
        return true;
    }

    @Override
    public boolean updatePost(UUID postId, PostCreateDTO postDTO) {
        Optional<Post> post = postRepository.findById(postId);
        if(post.isEmpty()) return false;
        Post upPost = post.get();
        upPost.setContent(postDTO.getContent());
        return true;
    }

    @Override
    public boolean deleteUser(UUID postId) {
        postRepository.deleteById(postId);
        return true;
    }
}
