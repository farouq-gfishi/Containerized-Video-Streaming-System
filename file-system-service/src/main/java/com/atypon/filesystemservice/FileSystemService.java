package com.atypon.filesystemservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@RestController
public class FileSystemService {

    @Value("${video.upload.dir:videos}")
    private String uploadDir;

    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file, @RequestParam String name, @RequestParam String path) {
        try {
            String fileName = name+".mp4";
            String filePath = uploadDir + File.separator + path + File.separator + fileName;
            File destinationDirectory = new File(uploadDir,path);
            if (!destinationDirectory.exists()) {
                destinationDirectory.mkdirs();
            }
            Path destinationFilePath = Path.of(filePath);
            Files.copy(file.getInputStream(), destinationFilePath, StandardCopyOption.REPLACE_EXISTING);
            return ResponseEntity.ok("File uploaded successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error uploading file: " + e.getMessage());
        }
    }

    @GetMapping("/get")
    public ResponseEntity<FileSystemResource> getVideo(@RequestParam String path, @RequestParam String name) {
        String videoFilePath = uploadDir + File.separator + path + File.separator + name + ".mp4";
        File videoFile = new File(videoFilePath);
        if (videoFile.exists()) {
            return ResponseEntity.ok()
                    .contentLength(videoFile.length())
                    .body(new FileSystemResource(videoFile));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}

