package com.forums.app.mapper;

import com.forums.app.dto.FileDTO;
import com.forums.app.dto.PostDTO;
import com.forums.app.dto.UserDTO;
import com.forums.app.modal.File;
import com.forums.app.modal.Post;
import com.forums.app.modal.User;
import com.forums.app.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostMapper {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private FileMapper fileMapper;

    public PostDTO toDTO(Post post){
        PostDTO postDTO = new PostDTO();
        if(post == null)
            return postDTO;
        if(post.getId() != null)
            postDTO.setId(post.getId());
        if(post.getTitle() != null)
            postDTO.setTitle(post.getTitle());
        if(post.getContent() != null)
            postDTO.setContent(post.getContent());
        if(post.getUser() != null){
            UserDTO userDTO = userMapper.toDTO(post.getUser());
            postDTO.setUser(userDTO);
        }
        if(post.getFiles() != null){
            List<FileDTO> fileDTOs = fileMapper.toDTOList(post.getFiles());
            postDTO.setFiles(fileDTOs);
        }
        if(post.getTags().size() != 0)
            postDTO.setTags(post.getTags());
        return postDTO;
    }

    public List<PostDTO> toDTOList(List<Post> posts){
        List<PostDTO> postDTOs = new ArrayList<>();
        for(Post post: posts)
            postDTOs.add(toDTO(post));
        return postDTOs;
    }

    public Post toEntity(PostDTO postDTO){
        Post post = new Post();
        if (postDTO == null)
            return post;
        if (postDTO.getId() != null)
            post.setId(postDTO.getId());
        if (postDTO.getTitle() != null)
            post.setTitle(postDTO.getTitle());
        if (postDTO.getContent() != null)
            post.setContent(postDTO.getContent());
        if (postDTO.getUser() != null){
            User user = userMapper.toEntity(postDTO.getUser());
            post.setUser(user);
        }
        if(postDTO.getFiles() != null){
            List<File> files = fileMapper.toEntityList(postDTO.getFiles());
            post.setFiles(files);
        }
        if(postDTO.getTags().size() != 0)
            post.setTags(postDTO.getTags());
        return post;
    }

    public List<Post> toEntityList(List<PostDTO> postDTOs){
        List<Post> postList = new ArrayList<>();
        for(PostDTO post: postDTOs)
            postList.add(toEntity(post));
        return postList;
    }
}
