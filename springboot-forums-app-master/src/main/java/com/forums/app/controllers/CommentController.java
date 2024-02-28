package com.forums.app.controllers;

import com.forums.app.dto.CommentDTO;
import com.forums.app.modal.Comment;
import com.forums.app.modal.User;
import com.forums.app.repository.CommentRepository;
import com.forums.app.service.CommentService;
import com.forums.app.service.UserService;
import com.forums.app.util.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/forums")
@CrossOrigin
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserService userService;

    @PostMapping("/post/comment")
    public GenericResponse comment(@RequestParam("username") String username,
                                   @RequestParam("postId") Long postId,
                                   @RequestParam("text") String text)
    {
        User currentUser = userService.finByUsername(username);
        if (currentUser != null){
            CommentDTO comment = commentService.addCommentToPost(postId, currentUser.getId(), text);
            if(comment != null)
                return new GenericResponse(200, "Comment added successfully", comment);
        }
        return new GenericResponse(500, "Unable to add comment");
    }

    @GetMapping("/post/comments/{postId}")
    public GenericResponse renderCommentsByPostId(@PathVariable Long postId){
        List<CommentDTO> commentDTOList = commentService.findAllByPost(postId);
        if(commentDTOList.size() > 0)
            return new GenericResponse(200, "Comments found", commentDTOList);
        return new GenericResponse(404, "Comments not found for post ID"+postId);
    }

    @PostMapping("/comments/edit/{commentId}")
    public GenericResponse editComment(@PathVariable Long commentId, @RequestParam("text") String updatedContent){
        CommentDTO updatedComment = commentService.edit(commentId, updatedContent);
        if(updatedComment == null)
            return new GenericResponse(500, "Unable to edit comment with id:"+commentId);
        return new GenericResponse(200, "Comment updated successfully......!", updatedComment);
    }

    @DeleteMapping("/comments/{commentId}")
    public GenericResponse delete(@PathVariable Long commentId){
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        if(optionalComment.isPresent()){
            commentRepository.deleteById(commentId);
            return new GenericResponse(200, "Comment deleted", Boolean.TRUE);
        }
        return new GenericResponse(500, "Unable to delete comment with id:"+commentId, Boolean.FALSE);
    }
}
