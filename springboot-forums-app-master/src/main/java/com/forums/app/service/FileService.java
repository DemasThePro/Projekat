package com.forums.app.service;

import com.forums.app.dto.FileDTO;
import com.forums.app.modal.File;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface FileService {
    FileDTO save(File file);

    void init();

    File upload(MultipartFile file);
}
