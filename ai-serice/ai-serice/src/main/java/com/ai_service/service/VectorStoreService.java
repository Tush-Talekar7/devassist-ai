package com.ai_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class VectorStoreService {

    private final VectorStore vectorStore;
    private final TokenTextSplitter tokenTextSplitter;

    /**
     * This method used to add the list of documents in the vector
     * @param documentList list of document
     */
    public void addDocuments(List<Document> documentList) {
        log.info("Creating chunks of the input data ");
        List<Document> chunks = tokenTextSplitter.apply(documentList);
        for (int i = 0; i < chunks.size(); i++) {
            chunks.get(i).getMetadata()
                    .put("chunkNumber", i + 1);
        }
        log.info("Adding the chunks in vector DB  ");
        vectorStore.add(chunks);
        log.info("Successfully added the chunks ");
    }

    /**
     * This method used to search in vector DB
     * @param query holds the user query
     * @return the related chunks.
     */
    public List<Document> search(String query) {

        SearchRequest searchRequest = SearchRequest.builder()
                .query(query)
                .topK(3)
                .build();

        return vectorStore.similaritySearch(searchRequest);
    }

}
