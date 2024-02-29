package com.atypon.videostreamingservice.repo;

import com.atypon.videostreamingservice.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepo extends JpaRepository<Video,Integer> {
}
