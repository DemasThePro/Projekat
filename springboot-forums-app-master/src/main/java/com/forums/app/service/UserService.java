package com.forums.app.service;

import com.forums.app.dto.UserDTO;
import com.forums.app.modal.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface UserService {

    User finByUsername(String username);

    UserDTO save(User user);

    List<UserDTO> findAll();

    boolean authenticate(String username, String password);

    User getByUsername(String username);

     Boolean changeProfilePicture(MultipartFile file, User user);
}
