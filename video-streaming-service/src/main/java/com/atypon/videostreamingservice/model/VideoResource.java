package com.atypon.videostreamingservice.model;

import lombok.Data;
import org.springframework.http.MediaType;

@Data
public class VideoResource {

    private final byte[] data;
    private final MediaType contentType;

    public VideoResource(byte[] data, MediaType contentType) {
        this.data = data;
        this.contentType = contentType;
    }

    public byte[] getData() {
        return data;
    }

    public MediaType getContentType() {
        return contentType;
    }
}

