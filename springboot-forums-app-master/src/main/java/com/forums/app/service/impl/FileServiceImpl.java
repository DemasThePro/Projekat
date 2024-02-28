package com.forums.app.service.impl;
import com.forums.app.dto.FileDTO;
import com.forums.app.mapper.FileMapper;
import com.forums.app.modal.File;
import com.forums.app.repository.FileRepository;
import com.forums.app.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;

@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private FileMapper fileTransformer;

    // Define the path for the local directory where the file will be stored

    private final Path rootPath = Paths.get("uploads");

    @Override
    public void init() {
        try {
            Files.createDirectories(rootPath);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    @Override
    public FileDTO save(File file) {
        File uploadedFile = fileRepository.save(file);
        return fileTransformer.toDTO(uploadedFile);
    }

    @Override
    public File upload(MultipartFile file) {
        init();
        String originalFilename = file.getOriginalFilename();
        String filename = originalFilename.replace(" ", "_");
        File uploadedFile = new File();
        try {
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, this.rootPath.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
            }
            uploadedFile.setName(filename);
            uploadedFile.setUrl(rootPath+"/"+filename);
        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                throw new RuntimeException("A file of that name already exists.");
            }
            throw new RuntimeException(e.getMessage());
        }
        return uploadedFile;
    }
}
