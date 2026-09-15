package com.document.controller;

import com.document.entity.Document;
import com.document.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping("/upload")
    public ResponseEntity<Document> uploadDocument(
            @RequestParam("file") MultipartFile file,
            @RequestParam("userId") Long userId) throws IOException {

        Document document = documentService.uploadDocument(file, userId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(document);
    }

    @GetMapping
    public ResponseEntity<List<Document>> getAllDocuments() {

        return ResponseEntity.ok(
                documentService.getAllDocuments()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Document> getDocumentById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                documentService.getDocumentById(id)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocument(
            @PathVariable Long id) {

        documentService.deleteDocument(id);

        return ResponseEntity.noContent().build();
    }
}
