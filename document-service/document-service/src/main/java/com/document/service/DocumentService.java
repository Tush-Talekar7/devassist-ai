package com.document.service;

import com.document.entity.Document;
import com.document.enums.DocumentStatus;
import com.document.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;

    public Document uploadDocument(MultipartFile file, Long userId) throws IOException {

        String uploadDirectory = "uploads";

        Path directory = Paths.get(uploadDirectory);

        if (!Files.exists(directory)) {
            Files.createDirectories(directory);
        }

        String fileName = file.getOriginalFilename();

        Path filePath = directory.resolve(fileName);

        Files.copy(
                file.getInputStream(),
                filePath,
                StandardCopyOption.REPLACE_EXISTING
        );

        Document document = Document.builder()
                .fileName(fileName)
                .fileType(file.getContentType())
                .filePath(filePath.toString())
                .fileSize(file.getSize())
                .status(DocumentStatus.UPLOADED)
                .uploadedBy(userId)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        return documentRepository.save(document);
    }

    public List<Document> getAllDocuments() {
        return documentRepository.findAll();
    }

    public Document getDocumentById(Long id) {

        return documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found"));
    }

    public void deleteDocument(Long id) {

        Document document = getDocumentById(id);

        try {
            Files.deleteIfExists(Paths.get(document.getFilePath()));
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete file", e);
        }

        documentRepository.delete(document);
    }
}
