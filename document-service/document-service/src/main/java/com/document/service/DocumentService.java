package com.document.service;

import com.document.client.AIServiceClient;
import com.document.entity.Document;
import com.document.enums.DocumentStatus;
import com.document.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
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
@Slf4j
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final AIServiceClient aiServiceClient;

    /**
     * This method is used to upload the document.It will save the document metadata in postgre DB and then
     * call the AI service to store it in vector DB.
     * @param file holds the uploaded file
     * @param userId holds the userID
     * @return the entity object
     * @throws IOException exception
     */
    public Document uploadDocument(MultipartFile file, Long userId) throws IOException {
        log.info("Started uploading document ");
        String uploadDirectory = "uploads";
        Path directory = Paths.get(uploadDirectory);
        if (!Files.exists(directory)) {
            Files.createDirectories(directory);
        }
        String fileName = file.getOriginalFilename();
        Path filePath = directory.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        byte[] fileBytes = file.getBytes();
        log.info("Creating document entity ");
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
        document =  documentRepository.save(document);
        log.info("Saved the document in database");
        // calling ai - service

        log.info("Calling AI-service to store the file data in vector store");
        String response = aiServiceClient.sendDocument(new ByteArrayResource(fileBytes));
        log.info("Received from AI-Service {}",response );
        return document;
    }

}
