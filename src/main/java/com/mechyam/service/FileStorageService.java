package com.mechyam.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Service
public class FileStorageService {

    // 🔥 MUST BE ABSOLUTE PATH
    private static final Path RESUME_DIR = Paths.get("/uploads/resumes");

    public String storeResume(MultipartFile file) throws IOException {

        if (!file.getContentType().equalsIgnoreCase("application/pdf")) {
            throw new RuntimeException("Only PDF resumes are supported");
        }

        Files.createDirectories(RESUME_DIR);

        String fileName = UUID.randomUUID() + ".pdf";
        Path targetPath = RESUME_DIR.resolve(fileName);

        Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

        return fileName;
    }

    public Path loadResume(String fileName) {
        return RESUME_DIR.resolve(fileName).normalize();
    }

    public Path loadFile(String fileName) {
        return loadResume(fileName);
    }
}

