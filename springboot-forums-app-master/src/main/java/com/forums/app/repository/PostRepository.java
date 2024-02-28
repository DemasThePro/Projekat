package com.forums.app.repository;

import com.forums.app.modal.Post;
import com.forums.app.modal.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByUser(User user);
    List<Post> findAllByUserOrderByIdDesc(User user);
    List<Post> findAllByOrderByIdDesc();
}
