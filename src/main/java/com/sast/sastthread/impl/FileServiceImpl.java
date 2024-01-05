package com.sast.sastthread.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import com.sast.sastthread.exception.FileException;
import com.sast.sastthread.exception.FileNotFoundException;
import com.sast.sastthread.properties.FileProperties;
import com.sast.sastthread.service.FileService;

public class FileServiceImpl implements FileService {
    private final Path root;

    public FileServiceImpl(FileProperties properties) {
        if (properties.getLocation().trim().length() == 0) {
            throw new FileException("File upload location can not be Empty.");
        }

        this.root = Paths.get(properties.getLocation());
    }

    @Override
    public void uploadFile(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new FileException("Failed to store empty file.");
            }
            Path destinationFile = this.root.resolve(
                    Paths.get(file.getOriginalFilename()))
                    .normalize().toAbsolutePath();
            if (!destinationFile.getParent().equals(this.root.toAbsolutePath())) {
                throw new FileException(
                        "Cannot store file outside current directory.");
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile,
                        StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            throw new FileException("Failed to store file.", e);
        }
    }

    @Override
    public void deleteFile() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    @Override
    public Path load(String filename) {
        return root.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new FileNotFoundException(
                        "Could not read file: " + filename);

            }
        } catch (MalformedURLException e) {
            throw new FileNotFoundException("Could not read file: " + filename, e);
        }
    }

}
