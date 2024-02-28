package com.forums.app.service;

import com.forums.app.dto.CommentDTO;
import com.forums.app.modal.Comment;
import com.forums.app.modal.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {
    CommentDTO addCommentToPost(Long postId, Long userId, String commentContent);
    List<CommentDTO> findAllByPost(Long postId);
    List<CommentDTO> findByUser(User user);
    CommentDTO edit(Long commentId, String updatedContent);

}
