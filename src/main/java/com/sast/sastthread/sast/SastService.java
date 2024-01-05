package com.sast.sastthread.sast;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonNode;

public interface SastService {
    JsonNode process(MultipartFile file);
}
