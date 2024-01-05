package com.sast.sastthread.impl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sast.sastthread.validation.FileValidatorService;

import io.jsonwebtoken.io.IOException;

@Service
public class FileValidatorServiceImpl implements FileValidatorService {

    @Override
    public boolean isValidMimeType(String mimeType) {
        return mimeType.startsWith("image/");
    }

    @Override
    public boolean isValidFileSize(Long fileSize) {
        long maxSize = 10 * 1024 * 1024;
        return fileSize <= maxSize;
    }

    @Override
    public boolean isValidContentType(String contentType) {
        return "application/json".equals(contentType);
    }

    @Override
    public boolean isFileExist(String filePath) {
        try {
            Path path = Paths.get(filePath);
            return Files.exists(path);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean isValidFileName(String filename) {
        return filename.equals(filename);
    }

    @Override
    public boolean validate(MultipartFile file) {
        return true;
    }

}
