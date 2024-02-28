package com.forums.app.mapper;

import com.forums.app.dto.FileDTO;
import com.forums.app.dto.UserDTO;
import com.forums.app.modal.File;
import com.forums.app.modal.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FileMapper {


    public FileDTO toDTO(File file){
        FileDTO fileDTO = new FileDTO();
        if(file == null)
            return null;
        if(file.getId() != null)
            fileDTO.setId(file.getId());
        if(file.getName() != null)
            fileDTO.setName(file.getName());
        if(file.getUrl() != null)
            fileDTO.setUrl(file.getUrl());
        return fileDTO;
    }

    public List<FileDTO> toDTOList(List<File> files){
        List<FileDTO> fileDTOs = new ArrayList<FileDTO>();
        for (File file : files){
            fileDTOs.add(toDTO(file));
        }
        return fileDTOs;
    }

    public File toEntity(FileDTO fileDTO){
        File file = new File();
        if(fileDTO == null)
            return null;
        if(fileDTO.getId() != null)
            file.setId(fileDTO.getId());
        if(fileDTO.getName() != null)
            file.setName(fileDTO.getName());
        if(fileDTO.getUrl() != null)
            file.setUrl(fileDTO.getUrl());
        return file;
    }

    public List<File> toEntityList(List<FileDTO>  fileDTOs){
        List<File> files = new ArrayList<File>();
        for (FileDTO file : fileDTOs){
            files.add(toEntity(file));
        }
        return files;
    }
}
