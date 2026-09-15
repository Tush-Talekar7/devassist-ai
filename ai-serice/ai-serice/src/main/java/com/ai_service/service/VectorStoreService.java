package com.ai_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VectorStoreService {

    private final VectorStore vectorStore;
    public void addText(String text) {

        Document document = new Document(text);

        vectorStore.add(List.of(document));
    }

    public List<Document> search(String query) {

        SearchRequest searchRequest = SearchRequest.builder()
                .query(query)
                .topK(3)
                .build();

        return vectorStore.similaritySearch(searchRequest);
    }

}
