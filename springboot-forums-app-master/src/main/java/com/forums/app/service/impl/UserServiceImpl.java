package com.forums.app.service.impl;

import com.forums.app.dto.UserDTO;
import com.forums.app.mapper.UserMapper;
import com.forums.app.modal.File;
import com.forums.app.modal.User;
import com.forums.app.repository.UserRepository;
import com.forums.app.service.FileService;
import com.forums.app.service.UserService;
import com.forums.app.util.PasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static final ThreadLocal<User> currentUser = new ThreadLocal<>();

    @Autowired
    private FileService fileService;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Override
    public User finByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserDTO save(User user) {
        // Encoding password before saving to database
        String encodedPassword = PasswordUtils.encodePassword(user.getPassword());
        user.setPassword(encodedPassword);
        user = userRepository.save(user);
        return userMapper.toDTO(user);
    }

    @Override
    public List<UserDTO> findAll() {
        List<User> users = userRepository.findAll();
        return userMapper.toDTOList(users);
    }

    @Override
    public boolean authenticate(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            System.err.println("User not found with username " + username);
            return false;
        }
        boolean matchPassword = PasswordUtils.matchPassword(password, user.getPassword());
        if (matchPassword) {
            return true;
        }
        return false;
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Boolean changeProfilePicture(MultipartFile file, User user) {
        // Uploading file to local storage
        File uploadedFile = fileService.upload(file);
        if(uploadedFile != null) {
            // Saving path of the uploaded file in the database
            user.setAvatar(uploadedFile.getName());
            // Saving user in the database
            User updatedUser = userRepository.save(user);
            if(updatedUser != null)
                return true;
        }
        return false;
    }
}
