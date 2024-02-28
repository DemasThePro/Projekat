package com.forums.app.mapper;

import com.forums.app.dto.LikeDTO;
import com.forums.app.modal.Like;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LikesMapper {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PostMapper postMapper;

    public LikeDTO toDTO(Like like){
        LikeDTO likeDTO = new LikeDTO();
        if(like == null)
            return likeDTO;
        if(like.getId() != null)
            likeDTO.setId(like.getId());
        if(like.getUser() != null)
            likeDTO.setUser(userMapper.toDTO(like.getUser()));
        if(like.getPost() != null)
            likeDTO.setPost(postMapper.toDTO(like.getPost()));
        return  likeDTO;
    }

    public List<LikeDTO> likeDTOList(List<Like> likes){
        List<LikeDTO> likeDTOList =  new ArrayList<>();
        for(Like like: likes)
            likeDTOList.add(toDTO(like));
        return likeDTOList;
    }
}
