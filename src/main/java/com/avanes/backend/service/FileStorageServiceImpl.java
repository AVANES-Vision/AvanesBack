package com.avanes.backend.service;


import com.avanes.backend.service.interfaces.FileStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    private String uploadDir = "uploads";

    @Override
    public String storeFile(MultipartFile file, String directory) throws IOException {
        // Normalize file name and add UUID to prevent name conflicts
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
        String fileExtension = getFileExtension(originalFileName);
        String fileName = UUID.randomUUID().toString() + fileExtension;

        // Create directory if it doesn't exist
        Path targetDir = Paths.get(directory).toAbsolutePath().normalize();
        Files.createDirectories(targetDir);

        // Copy file to target location
        Path targetLocation = targetDir.resolve(fileName);
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

        return fileName;
    }

    @Override
    public void deleteFile(String filePath) throws IOException {
        Path path = Paths.get(filePath).toAbsolutePath().normalize();
        Files.deleteIfExists(path);
    }

    private String getFileExtension(String fileName) {
        if (fileName == null || fileName.lastIndexOf(".") == -1) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf("."));
    }
}