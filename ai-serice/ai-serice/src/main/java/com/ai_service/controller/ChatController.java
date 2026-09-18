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

    @PostMapping("/chat")
    public ResponseEntity<String> chat(@RequestParam String question) {

        String response = aiService.chat(question);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/test-document/{id}")
    public ResponseEntity<String> testDocumentDownload(@PathVariable Long id, @RequestHeader("Authorization") String authorization) {

        return ResponseEntity.ok(aiService.testDocumentDownload(id, authorization));
    }
}
