package com.forums.app.mapper;

import com.forums.app.dto.CommentDTO;
import com.forums.app.modal.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentMapper {

    @Autowired
    private PostMapper postMapper;

    public CommentDTO toDTO(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        if(comment == null)
            return null;
        if(comment.getId() != null)
            commentDTO.setId(comment.getId());
        if(comment.getText() != null)
            commentDTO.setText(comment.getText());
        if(comment.getUser() != null)
            commentDTO.setCommentator(comment.getUser().getName());
        if(comment.getPost() != null)
            commentDTO.setPost(postMapper.toDTO(comment.getPost()));
        return commentDTO;
    }

    public List<CommentDTO> toDTOList(List<Comment> comments) {
        List<CommentDTO> list = new ArrayList<CommentDTO>();
        for(Comment comment: comments)
            list.add(toDTO(comment));
        return list;
    }

}
