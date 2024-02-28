package com.forums.app.repository;

import com.forums.app.modal.File;
import com.forums.app.modal.Post;
import com.forums.app.modal.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {

}
