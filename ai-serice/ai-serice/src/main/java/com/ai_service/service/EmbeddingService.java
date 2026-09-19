package com.ai_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmbeddingService {

    private final EmbeddingModel embeddingModel;

    /**
     * This will generate the embedding for the input question
     * @param text holds the input question
     * @return the vector array
     */
    public float[] generateEmbedding(String text) {
        return embeddingModel.embed(text);
    }
}
