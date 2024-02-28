package com.forums.app.mapper;

import com.forums.app.dto.UserDTO;
import com.forums.app.modal.User;
import com.forums.app.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserMapper {
    private final UserRepository userRepository;

    public UserMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO toDTO(User user){
        UserDTO userDTO = new UserDTO();
        if(user != null && user.getName() != null)
            userDTO.setName(user.getName());
        if(user != null && user.getUsername() != null)
            userDTO.setUsername(user.getUsername());
        if(user != null && user.getPassword() != null)
            userDTO.setPassword(user.getPassword());
        if(user.getAvatar() != null)
            userDTO.setAvatar(user.getAvatar());
        return userDTO;
    }

    public List<UserDTO> toDTOList(List<User> users){
        List<UserDTO> list = new ArrayList<UserDTO>();
        for(User user : users)
            list.add(toDTO(user));
        return list;
    }

    public User toEntity(UserDTO userDTO){
        User user = new User();
        if(userDTO != null && userDTO.getName() != null)
            user.setName(userDTO.getName());
        if(userDTO != null && userDTO.getUsername() != null)
            user.setUsername(userDTO.getUsername());
        if(userDTO != null && userDTO.getPassword() != null)
            user.setPassword(userDTO.getPassword());
        if(userDTO.getAvatar() != null)
            user.setAvatar(userDTO.getAvatar());
        return user;
    }

    public List<User> toEntityList(List<UserDTO> userDTOs){
        List<User> userList = new ArrayList<>();
        for(UserDTO userDTO : userDTOs)
            userList.add(toEntity(userDTO));
        return userList;
    }
}
