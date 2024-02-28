package com.forums.app.service.impl;

import com.forums.app.dto.FileDTO;
import com.forums.app.dto.PostDTO;
import com.forums.app.mapper.PostMapper;
import com.forums.app.mapper.UserMapper;
import com.forums.app.modal.*;
import com.forums.app.repository.CommentRepository;
import com.forums.app.repository.LikeRepository;
import com.forums.app.repository.PostRepository;
import com.forums.app.repository.UserRepository;
import com.forums.app.service.FileService;
import com.forums.app.service.PostService;
import com.forums.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostMapper postMapper;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private LikeRepository  likeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;
    @Autowired
    private FileService fileService;
    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<PostDTO> findAll() {
        List<Post> posts = postRepository.findAllByOrderByIdDesc();
        return postMapper.toDTOList(posts);
    }

    @Override
    public List<PostDTO> findAllByUser(User user) {
        List<Post> posts = postRepository.findAllByUserOrderByIdDesc(user);
        return findAllPostDTOs(postMapper.toDTOList(posts));
    }

    @Override
    public Boolean save(Post post, MultipartFile file, User currentUser){
        System.out.println("in PostServiceImpl.save()");
        FileDTO uploadedFileToDatabase = null;
        try{
            // Saving current user to database
            userRepository.save(currentUser);

            post.setUser(currentUser);
            // Saving post to database
            Post savedPost = postRepository.save(post);
            System.out.println("Post saved to database successfully");

            if(file != null){
                // Uploading file to local storage
                File uploadedFile = fileService.upload(file);
                uploadedFile.setPost(savedPost);
                System.out.println("Uploaded file to local storage successfully");

                // Uploading file to database
                uploadedFileToDatabase = fileService.save(uploadedFile);
                System.out.println("Uploaded file to database successfully");
            }
        }catch (Exception e){
            System.err.println("Exception in PostServiceImpl.save() >>> "+e.getMessage());
            e.printStackTrace();
            return false;
        }
        return true;

    }

    @Override
    public Integer countLikesForPost(Long postId) {
        List<Like> likes = new ArrayList<Like>();
        Post post = postRepository.findById(postId).get();
        if(post != null)
             likes = likeRepository.findByPost(post);
        return likes.size();
    }

    @Override
    public Integer countCommentsForPost(Long postId) {
        List<Comment> comments = new ArrayList<Comment>();
        Post post = postRepository.findById(postId).get();
        if(post != null)
            comments = commentRepository.findByPost(post);
        return comments.size();
    }

    @Override
    public List<PostDTO> findAllWithLikes() {
        List<Post> posts = postRepository.findAllByOrderByIdDesc();
        return findAllPostDTOs(postMapper.toDTOList(posts));
    }

    @Override
    public PostDTO findById(Long postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if(optionalPost.isPresent()) {
            return postMapper.toDTO(optionalPost.get());
        }
        return null;
    }

    private List<PostDTO> findAllPostDTOs(List<PostDTO> postDTOs) {
        List<PostDTO> posts = new ArrayList<PostDTO>();
        for(PostDTO postDTO : postDTOs){
            postDTO.setLikesCounter(countLikesForPost(postDTO.getId()));
            postDTO.setCommentsCounter(countCommentsForPost(postDTO.getId()));
            posts.add(postDTO);
        }
        return posts;
    }
}
