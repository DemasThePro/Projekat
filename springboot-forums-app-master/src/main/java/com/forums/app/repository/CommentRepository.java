package com.forums.app.repository;

import com.forums.app.modal.Comment;
import com.forums.app.modal.Post;
import com.forums.app.modal.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPost(Post post);

    List<Comment> findByPost(Post post);
    List<Comment> findByUser(User user);
}
