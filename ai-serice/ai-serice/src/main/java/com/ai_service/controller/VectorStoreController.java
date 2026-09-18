package com.ai_service.controller;

import com.ai_service.service.RagService;
import com.ai_service.service.VectorStoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/vector")
@RequiredArgsConstructor
@Slf4j
public class VectorStoreController {

    private final VectorStoreService vectorStoreService;
    private final RagService ragService;

//    @PostMapping("/add")
//    public ResponseEntity<String> addText(@RequestParam String text) {
//        log.info("Adding the text into vector db");
//        vectorStoreService.addText(text);
//        return ResponseEntity.ok("Text added successfully");
//    }

    @PostMapping("/search")
    public ResponseEntity<String> search(@RequestParam String text){
        log.info("Searching ....");
        return ResponseEntity.ok(ragService.answerQuestion(text));
    }
}
