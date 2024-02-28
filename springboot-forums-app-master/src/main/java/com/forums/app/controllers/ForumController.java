package com.forums.app.controllers;

import com.forums.app.dto.PostDTO;
import com.forums.app.modal.Like;
import com.forums.app.modal.Post;
import com.forums.app.modal.User;
import com.forums.app.repository.PostRepository;
import com.forums.app.service.LikeService;
import com.forums.app.service.PostService;
import com.forums.app.service.UserService;
import com.forums.app.util.GenericResponse;
import com.forums.app.util.TagsExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/forums")
public class ForumController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private LikeService likeService;
    @Autowired
    private PostRepository postRepository;

    @GetMapping
    public GenericResponse findAll() {
        List<PostDTO> posts = postService.findAllWithLikes();
        if (posts.size() > 0)
            return new GenericResponse(200, "Success", posts);
        return new GenericResponse(200, "No results found", null);
    }

    @PostMapping("/create/{username}")
    public GenericResponse save(HttpServletRequest request,
                                @PathVariable("username") String username,
                                @RequestParam("title") String title,
                                @RequestParam("content") String content,
                                @RequestParam(value = "file", required = false) MultipartFile file,
                                HttpSession session) {
        System.out.println("in PostController.save()");
        User currentUser = userService.getByUsername(username);
        Post post = new Post(title, content);
        post.setTags(TagsExtractor.extract(content));
        Boolean savedPost = postService.save(post, file, currentUser);
        if (savedPost)
            return new GenericResponse(200, "Post saved successfully");
        return new GenericResponse(500, "Unable to save post");
    }

    @PostMapping("/post/like")
    public GenericResponse likePost(@RequestParam("username") String username, @RequestParam("postId") Long postId){
        User user = userService.finByUsername(username);
        Like like = likeService.likePost(postId, user.getId());
        if(like != null)
            return new GenericResponse(200, "Post liked successfully");
        return new GenericResponse(500, "Unable to like post");
    }

    @GetMapping("/post/{postId}")
    public GenericResponse findById(@PathVariable Long postId){
        PostDTO post = postService.findById(postId);
        if(post != null)
            return new GenericResponse(200, "Post found", post);
        return new GenericResponse(404, "Post not found");
    }

    @GetMapping("/posts/users/{username}")
    public GenericResponse findAllByUsername(@PathVariable String username){
        User user = userService.finByUsername(username);
        if (user == null)
            return new GenericResponse(404, "User not found");
        List<PostDTO> posts = postService.findAllByUser(user);
        return new GenericResponse(200, posts.size() +" posts found for user " + username, posts);
    }

    @PostMapping("/keywords-extraction")
    public List<String> extractKeywords(@RequestParam String text){
        return TagsExtractor.extract(text);
    }

    @DeleteMapping("/posts/dislike/{username}/{postId}")
    public GenericResponse dislikePost(@PathVariable("username") String username, @PathVariable("postId") Long postId){
        User user = userService.finByUsername(username);
        if(user == null)
            return new GenericResponse(404, "User not found");
        Boolean disliked = likeService.disLikePost(postId, username);
        return disliked ?
                new GenericResponse(200, "Disliked post"):
                new GenericResponse(500, "Internal Server Error occurred while disliking post");
    }

    @DeleteMapping("/posts/{postId}")
    public GenericResponse delete(@PathVariable Long postId){
        Optional<Post> optionalPost = postRepository.findById(postId);
        if(optionalPost.isPresent()){
            postRepository.deleteById(postId);
            return new GenericResponse(200, "Post deleted", Boolean.TRUE);
        }
        return new GenericResponse(500, "Unable to delete post with id:"+postId, Boolean.FALSE);
    }
}
