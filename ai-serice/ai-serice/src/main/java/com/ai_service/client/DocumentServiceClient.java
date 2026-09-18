package com.ai_service.client;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class DocumentServiceClient {

    private final RestClient documentClient;

    public byte[] downloadDocument(Long documentId,String auth) {
        String tokenValue = auth.startsWith("Bearer ") ? auth : "Bearer " + auth;

        return documentClient
                .get()
                .uri("/api/documents/download/{id}", documentId)
                .header(HttpHeaders.AUTHORIZATION, tokenValue)
                .retrieve()
                .body(byte[].class);
    }
}
