package com.sast.sastthread.sast.impl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sast.sastthread.sast.SastService;
import com.sast.sastthread.service.FileService;

import io.jsonwebtoken.io.IOException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SastServiceImpl implements SastService {

    @Value("{sast.location}")
    private Path sastRoot;
    private FileService fileService;

    @Async
    @Override
    public JsonNode process(MultipartFile file) throws IOException {
        try {
            Path filePath = fileService.load(file.getOriginalFilename());
            String[] command = { "python3 sucosh.py -p", filePath.toString() };
            ProcessBuilder sastProcessBuilder = new ProcessBuilder(command);

            Process sastProcess = sastProcessBuilder.start();

            InputStream inputStream = sastProcess.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder output = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            String jsonOutput = output.toString();

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonOutput);

            return jsonNode;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
