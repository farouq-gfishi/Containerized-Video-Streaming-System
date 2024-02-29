package com.atypon.videostreamingservice.controller;

import com.atypon.videostreamingservice.model.Video;
import com.atypon.videostreamingservice.repo.VideoRepo;
import com.atypon.videostreamingservice.model.VideoResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;


@Controller
public class VideoStreamingController {

    private final String AUTHENTICATION_SERVICE = "http://authentication-service:8080/login";
    private final String FILE_SYSTEM_SERVICE = "http://file-system-service:8080/get";

    private final RestTemplate restTemplate;
    private final VideoRepo videoRepo;

    @Autowired
    public VideoStreamingController(RestTemplate restTemplate, VideoRepo videoRepo) {
        this.restTemplate = restTemplate;
        this.videoRepo = videoRepo;
    }

    @PostMapping("/login")
    public String authenticate(@RequestParam String id, @RequestParam String password, Model model) {
        String uri = UriComponentsBuilder.fromUriString(AUTHENTICATION_SERVICE)
                .queryParam("id", id)
                .queryParam("password", password)
                .toUriString();
        Boolean result = restTemplate.getForObject(uri, Boolean.class);
        if (Boolean.TRUE.equals(result)) {
            return "redirect:/read";
        } else {
            return "redirect:/index.html";
        }
    }

    @GetMapping("/read")
    public String getVideos(Model model) {
        List<Video> videos = videoRepo.findAll();
        List<VideoResource> videoResources = new ArrayList<>();
        List<String>names = new ArrayList<>();
        for(Video video:videos) {
            String path = video.getPath();
            String name = video.getName();
            String getVideoUri = UriComponentsBuilder.fromUriString(FILE_SYSTEM_SERVICE)
                    .queryParam("path", path)
                    .queryParam("name", name)
                    .toUriString();
            ResponseEntity<byte[]> response = restTemplate.getForEntity(getVideoUri, byte[].class);
            if (response.getStatusCode().is2xxSuccessful()) {
                VideoResource videoResource = new VideoResource(response.getBody(), response.getHeaders().getContentType());
                videoResources.add(videoResource);
                names.add(name);
            }
        }
        model.addAttribute("videoResources",videoResources);
        model.addAttribute("names",names);
        return "video-streaming";
    }
}
