package com.document.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
@Slf4j
public class AIServiceClient {
    private final RestClient restClient;

    /**
     * This method will send the uploaded document to the AI-Service to store it in vector DB
     * @param resource holds the uploaded document
     * @return response
     */
    public String sendDocument(Resource resource){
        try {
            restClient.post()
                    .uri("/ai/processPDF")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(resource) // Spring automatically streams the Resource as bytes
                    .retrieve()
                    .toBodilessEntity();
        }catch (RuntimeException e){
         log.error("Exception occurred while calling AI-Service {}" , e);
        }
        return "Document Sent successfully";
    }


}
