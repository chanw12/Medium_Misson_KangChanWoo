package com.ll.medium.domain.file.controller;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class FileUploadController {

    private final AmazonS3 amazonS3;
    private final String bucketName = "medium";



    @PostMapping("/api/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {
        String objectName = file.getOriginalFilename();

        try {
            amazonS3.putObject(new PutObjectRequest(bucketName, objectName, file.getInputStream(), null));
            return "File uploaded successfully: " + objectName;
        } catch (IOException e) {
            e.printStackTrace();
            return "File upload failed: " + e.getMessage();
        }
    }
}