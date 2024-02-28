package com.forums.app.service.impl;

import com.forums.app.dto.CommentDTO;
import com.forums.app.mapper.CommentMapper;
import com.forums.app.modal.Comment;
import com.forums.app.modal.Post;
import com.forums.app.modal.User;
import com.forums.app.repository.CommentRepository;
import com.forums.app.repository.PostRepository;
import com.forums.app.repository.UserRepository;
import com.forums.app.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public CommentDTO addCommentToPost(Long postId, Long userId, String commentContent) {
        Optional<User> optionalUser = userRepository.findById(userId);
        Optional<Post> optionalPost = postRepository.findById(postId);
        if(optionalUser.isPresent() && optionalPost.isPresent()) {
            Comment comment = new Comment();
            comment.setUser(optionalUser.get());
            comment.setPost(optionalPost.get());
            comment.setText(commentContent);
            commentRepository.save(comment);
            return commentMapper.toDTO(comment);
        }
        System.out.println("User or post not found");
        return null;
    }

    @Override
    public List<CommentDTO> findAllByPost(Long postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if(optionalPost.isPresent()) {
            List<Comment> comments = commentRepository.findAllByPost(optionalPost.get());
            return commentMapper.toDTOList(comments);
        }
        System.out.println("Post not found with post ID " + postId);
        return null;
    }

    @Override
    public List<CommentDTO> findByUser(User user) {
        List<Comment> comments = commentRepository.findByUser(user);
        return commentMapper.toDTOList(comments);
    }

    @Override
    public CommentDTO edit(Long commentId, String updatedContent) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        if(!optionalComment.isPresent())
            return null;
        Comment comment = optionalComment.get();
        comment.setText(updatedContent);
        Comment updatedComment = commentRepository.save(comment);
        if(updatedComment == null)
            return null;
        return commentMapper.toDTO(updatedComment);
    }
}
