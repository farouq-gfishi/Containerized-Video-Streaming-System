package com.atypon.uploadvideo.controller;

import com.atypon.uploadvideo.model.Video;
import com.atypon.uploadvideo.repo.VideoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;


@Controller
public class UploadVideoController {


    private final String AUTHENTICATION_SERVICE = "http://authentication-service:8080/login";
    private final String FILE_SYSTEM_SERVICE = "http://file-system-service:8080/upload";
    private final VideoRepo videoRepo;
    private final RestTemplate restTemplate;

    @Autowired
    public UploadVideoController(VideoRepo videoRepo, RestTemplate restTemplate) {
        this.videoRepo = videoRepo;
        this.restTemplate = restTemplate;
    }

    @PostMapping("/login")
    public String authenticate(@RequestParam String id, @RequestParam String password) {
        String uri = UriComponentsBuilder.fromUriString(AUTHENTICATION_SERVICE)
                .queryParam("id", id)
                .queryParam("password", password)
                .toUriString();
        Boolean result = restTemplate.getForObject(uri, Boolean.class);
        if (Boolean.TRUE.equals(result)) {
            return "redirect:/upload-video.html";
        } else {
            return "redirect:/index.html";
        }
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, @RequestParam String name, @RequestParam String path) {
        try {
            // Store video info to the db
            Video video = new Video();
            video.setName(name);
            video.setPath(path);
            videoRepo.save(video);

            // Prepare the request headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            // Prepare the request body
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file", new FileSystemResource(convert(file)));
            body.add("name", name);
            body.add("path", path);

            // Prepare the request entity
            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            // Make the request to the API
            restTemplate.exchange(FILE_SYSTEM_SERVICE, HttpMethod.POST, requestEntity, String.class);
            return "redirect:/upload-video.html";
        } catch (Exception e) {
            return "redirect:/";
        }
    }

    // Helper method to convert MultipartFile to File
    private File convert(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        Files.copy(file.getInputStream(), convFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        return convFile;
    }


}