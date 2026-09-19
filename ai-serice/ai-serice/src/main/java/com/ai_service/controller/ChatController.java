package com.ai_service.controller;

import com.ai_service.service.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class ChatController {

    private final AiService aiService;

    /**
     * This API is used to return the document requested by the AI-Service.
     * @param id holds the document ID
     * @param authorization holds the JWT token
     * @return response
     */
    @GetMapping("/test-document/{id}")
    public ResponseEntity<String> testDocumentDownload(@PathVariable Long id, @RequestHeader("Authorization") String authorization) {

        return ResponseEntity.ok(aiService.testDocumentDownload(id, authorization));
    }
}
