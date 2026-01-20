package com.mechyam.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Service
public class FileStorageService {

    private static final Path RESUME_DIR = Paths.get("/uploads/resumes");

    public String storeResume(MultipartFile file) throws IOException {

        if (!"application/pdf".equalsIgnoreCase(file.getContentType())) {
            throw new RuntimeException("Only PDF resumes are supported");
        }

        Files.createDirectories(RESUME_DIR);

        String fileName = UUID.randomUUID() + ".pdf";
        Path targetPath = RESUME_DIR.resolve(fileName);

        Files.copy(
                file.getInputStream(),
                targetPath,
                StandardCopyOption.REPLACE_EXISTING
        );

        return fileName; // store ONLY filename in DB
    }

    public Path loadResume(String fileName) {
        return RESUME_DIR.resolve(fileName).normalize();
    }
}

