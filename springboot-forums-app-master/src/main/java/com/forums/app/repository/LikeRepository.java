package com.forums.app.repository;

import com.forums.app.modal.Like;
import com.forums.app.modal.Post;
import com.forums.app.modal.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    List<Like> findByPost(Post post);
    Boolean existsByPostAndUser(Post post, User user);
    Like findByPostAndUser(Post post, User user);
    List<Like> findByUser(User user);
}
