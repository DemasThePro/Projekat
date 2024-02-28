package com.forums.app.dto;

import java.util.List;

public class PostDTO {
    private Long id;
    private String title;
    private String content;

    private List<String> tags;

    private Integer likesCounter;
    private Integer commentsCounter;

    private UserDTO user;

    private List<FileDTO> files;

    public PostDTO(){}

    public PostDTO(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public List<FileDTO> getFiles() {
        return files;
    }

    public void setFiles(List<FileDTO> files) {
        this.files = files;
    }

    public Integer getLikesCounter() {
        return likesCounter;
    }
    public void setLikesCounter(Integer likesCounter) {
        this.likesCounter = likesCounter;
    }

    public Integer getCommentsCounter() {
        return commentsCounter;
    }

    public void setCommentsCounter(Integer commentsCounter) {
        this.commentsCounter = commentsCounter;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
