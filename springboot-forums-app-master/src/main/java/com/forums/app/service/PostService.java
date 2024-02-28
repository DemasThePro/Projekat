package com.forums.app.service;

import com.forums.app.dto.PostDTO;
import com.forums.app.modal.Post;
import com.forums.app.modal.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface PostService {

    List<PostDTO> findAll();

    List<PostDTO> findAllByUser(User user);
    Boolean save(Post post, MultipartFile file, User currentUser);

    Integer countLikesForPost(Long postId);
    Integer countCommentsForPost(Long postId);

    List<PostDTO> findAllWithLikes();

    PostDTO findById(Long postId);


}
