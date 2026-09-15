package com.ai_service.controller;

import com.ai_service.service.EmbeddingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class EmbeddingController {

    private final EmbeddingService embeddingService;

    @PostMapping("/embedding")
    public ResponseEntity<float[]> generateEmbedding(
            @RequestParam String text) {

        float[] embedding = embeddingService.generateEmbedding(text);
        return ResponseEntity.ok(embedding);
    }
}
