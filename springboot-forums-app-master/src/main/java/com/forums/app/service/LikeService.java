package com.forums.app.service;

import com.forums.app.dto.LikeDTO;
import com.forums.app.modal.Like;
import com.forums.app.modal.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LikeService {
    Like likePost(Long postId, Long userId);
    Boolean disLikePost(Long postId, String username);
    List<LikeDTO> findByUser(User user);

}
