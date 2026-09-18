package com.document.controller;

import com.document.entity.Document;
import com.document.service.DocumentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping("/upload")
    public ResponseEntity<Document> uploadDocument(
            @RequestParam("file") MultipartFile file,
            @RequestParam("userId") String userId) throws IOException {

        Document document = documentService.uploadDocument(file, Long.valueOf(userId));

        return ResponseEntity.status(HttpStatus.CREATED).body(document);
    }

    @GetMapping
    public ResponseEntity<List<Document>> getAllDocuments() {

        return ResponseEntity.ok(
                documentService.getAllDocuments()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Document> getDocumentById(@PathVariable Long id) {

        return ResponseEntity.ok(documentService.getDocumentById(id)
        );
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downLoadFile(@PathVariable Long id){
      try {
            Document document = documentService.getDocumentById(id);
            Path storageLocation = Paths.get("D:/Switch_Learning/LearningProject/uploads")
                    .toAbsolutePath().normalize();
            Path filePath = storageLocation.resolve(document.getFileName()).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (!resource.exists() || !resource.isReadable()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(resource);
        }catch(RuntimeException | MalformedURLException e){
          log.error("Error occurred {}", e);
      }
      return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocument(
            @PathVariable Long id) {

        documentService.deleteDocument(id);

        return ResponseEntity.noContent().build();
    }
}
