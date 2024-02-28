package com.forums.app.controllers;

import com.forums.app.dto.UserDTO;
import com.forums.app.mapper.UserMapper;
import com.forums.app.modal.User;
import com.forums.app.repository.UserRepository;
import com.forums.app.service.CommentService;
import com.forums.app.service.LikeService;
import com.forums.app.service.UserService;
import com.forums.app.util.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.forums.app.service.UserService;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LikeService likeService;

    @Autowired
    private CommentService commentService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/home")
    public String home(Model model) {
        List<UserDTO> dataList = userService.findAll();
        model.addAttribute("data", dataList);
        return "home"; // Assuming "home" is your Thymeleaf template name
    }

    @GetMapping
    public GenericResponse findAll(){
        List<UserDTO> userDTOs = userService.findAll();
        if(userDTOs != null)
            return new GenericResponse(200, "Users found", userDTOs);
        return new GenericResponse(200, "Users found", null);
    }

    @PostMapping
    public GenericResponse save(@RequestBody UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        UserDTO savedUser = userService.save(user);
        if(savedUser != null)
            return new GenericResponse(200, "New user created", savedUser);
        return new GenericResponse(500, "Unable to create user");
    }

    @PostMapping("/authentication")
    public GenericResponse authenticate(@RequestBody UserDTO userDTO){
        boolean authenticatedUser = userService.authenticate(userDTO.getUsername(), userDTO.getPassword());
        if(authenticatedUser)
            return new GenericResponse(200, "Authentication successful");
        return new GenericResponse(500, "Incorrect username or password");
    }

    @PostMapping("/change-profile-photo")
    public GenericResponse changeProfilePhoto(@RequestParam("file") MultipartFile file, @RequestParam("username") String username){
        User user = userService.finByUsername(username);
        if(user == null)
            new GenericResponse(404, "User not found");
        Boolean isProfileUpdated = userService.changeProfilePicture(file, user);
        return isProfileUpdated ?
                new GenericResponse(200, "Profile picture updated successfully") :
                new GenericResponse(500, "Internal Server Error while updating profile picture");
    }

    @GetMapping("/{username}")
    public GenericResponse findByUsername(@PathVariable String username){
        if(username == null)
            return new GenericResponse(404, "User can't be null");
        User user = userService.finByUsername(username);
        if(user == null)
            return new GenericResponse(404, "User does not exist with username:"+username);
        return new GenericResponse(200, "User found", userMapper.toDTO(user));
    }

    @GetMapping("/likes/{username}")
    public GenericResponse findAllLikesByUser(@PathVariable String username){
        User user = userService.finByUsername(username);
        if(user == null)
            return new GenericResponse(404, "User not found");
        return new GenericResponse(200, "Your activity", likeService.findByUser(user));
    }
    @GetMapping("/comments/{username}")
    public GenericResponse findAllCommentsByUser(@PathVariable String username){
        User user = userService.finByUsername(username);
        if(user == null)
            return new GenericResponse(404, "User not found");
        return new GenericResponse(200, "Your activity", commentService.findByUser(user));
    }
}
