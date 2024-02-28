package com.forums.app.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/files")
@CrossOrigin
public class FilesController {
    @GetMapping("/readFile/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) throws MalformedURLException {
        Path file = Paths.get("uploads").resolve(filename);
        Resource resource = new UrlResource(file.toUri());
        if (resource.exists() || resource.isReadable()) {
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(resource);
        } else {
            throw new RuntimeException("Could not read the file!");
        }
    }
}
