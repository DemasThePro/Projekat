package com.forums.app.dto;

public class CommentDTO {
    private Long id;
    private String text;
    private String commentator;

    private PostDTO post;

    public CommentDTO(){}
    public CommentDTO(Long id, String text, String commentator) {
        this.id = id;
        this.text = text;
        this.commentator = commentator;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCommentator() {
        return commentator;
    }

    public void setCommentator(String commentator) {
        this.commentator = commentator;
    }

    public PostDTO getPost() {
        return post;
    }

    public void setPost(PostDTO post) {
        this.post = post;
    }
}
