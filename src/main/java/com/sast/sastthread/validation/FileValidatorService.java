package com.sast.sastthread.validation;

import org.springframework.web.multipart.MultipartFile;

public interface FileValidatorService {
    boolean isValidMimeType(String mimeType);

    boolean isValidFileSize(Long fileSize);

    boolean isValidContentType(String contentType);

    boolean isFileExist(String filename);

    boolean isValidFileName(String filename);

    boolean validate(MultipartFile file);
}
