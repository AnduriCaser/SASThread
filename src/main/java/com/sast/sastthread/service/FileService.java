package com.sast.sastthread.service;

import java.nio.file.Path;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    void init();

    void uploadFile(MultipartFile file);

    void deleteFile();

    Resource loadAsResource(String filename);

    Path load(String filename);
}
