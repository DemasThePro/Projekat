package com.forums.app.service.impl;

import com.forums.app.dto.LikeDTO;
import com.forums.app.mapper.LikesMapper;
import com.forums.app.modal.Like;
import com.forums.app.modal.Post;
import com.forums.app.modal.User;
import com.forums.app.repository.LikeRepository;
import com.forums.app.repository.PostRepository;
import com.forums.app.repository.UserRepository;
import com.forums.app.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LikeServiceImpl implements LikeService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private LikesMapper likesMapper;

    @Override
    public Like likePost(Long postId, Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        Optional<Post> optionalPost = postRepository.findById(postId);
        if(optionalUser.isPresent() && optionalPost.isPresent()) {
            Boolean existsByPostAndUser = likeRepository.existsByPostAndUser(optionalPost.get(), optionalUser.get());
            if (existsByPostAndUser) {
                System.out.println("Like already exists for this post and user");
            }
            else{
                Like like = new Like();
                like.setPost(optionalPost.get());
                like.setUser(optionalUser.get());
                return likeRepository.save(like);
            }
        }
        else{
            System.out.println("User or post doesn't exist");
        }
        return null;
    }


    @Override
    public Boolean disLikePost(Long postId, String username) {
        Optional<Post> post = postRepository.findById(postId);
        User user = userRepository.findByUsername(username);
        if (post.isPresent() && user != null) {
            Boolean existsByPostAndUser = likeRepository.existsByPostAndUser(post.get(), user);
            if(existsByPostAndUser){
                Like like = likeRepository.findByPostAndUser(post.get(), user);
                likeRepository.delete(like);
                return true;
            }
        }
        return false;
    }

    @Override
    public List<LikeDTO> findByUser(User user) {
        return likesMapper.likeDTOList(likeRepository.findByUser(user));
    }
}
