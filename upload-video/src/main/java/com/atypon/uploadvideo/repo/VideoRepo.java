package com.atypon.uploadvideo.repo;

import com.atypon.uploadvideo.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepo extends JpaRepository<Video,Integer> {
}
