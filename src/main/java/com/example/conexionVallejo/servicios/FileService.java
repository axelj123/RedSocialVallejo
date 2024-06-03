package com.example.conexionVallejo.servicios;

import com.example.conexionVallejo.modelos.FileEntity;
import com.example.conexionVallejo.modelos.Post;
import com.example.conexionVallejo.repositorios.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    public List<FileEntity> saveFiles(MultipartFile[] files, Post post) throws IOException {
        List<FileEntity> fileEntities = new ArrayList<>();
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                String fileName = file.getOriginalFilename();
                String fileUrl = saveFileToStorage(file);

                FileEntity fileEntity = new FileEntity();
                fileEntity.setFileName(fileName);
                fileEntity.setFileUrl(fileUrl);
                fileEntity.setPost(post);
                fileEntities.add(fileEntity);

                fileRepository.save(fileEntity);
            }
        }
        return fileEntities;
    }

    private String saveFileToStorage(MultipartFile file) throws IOException {
        String uploadDir = "/path/to/upload/dir/";
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        String fileName = file.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        return filePath.toString();
    }
}
