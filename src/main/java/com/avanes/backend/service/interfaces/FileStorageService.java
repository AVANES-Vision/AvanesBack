package com.avanes.backend.service.interfaces;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

public interface FileStorageService {
    String storeFile(MultipartFile file, String directory) throws IOException;
    void deleteFile(String filePath) throws IOException;
}