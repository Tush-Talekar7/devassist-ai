package com.ai_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RagService {

    private  VectorStore vectorStore;
    private  ChatClient chatClient;

    public RagService(VectorStore vectorStore1,ChatClient.Builder client){
        this.chatClient = client.build();
        this.vectorStore = vectorStore1;
    }

    public String answerQuestion(String question) {

        // 1. Retrieve relevant chunks
        SearchRequest searchRequest = SearchRequest.builder()
                .query(question)
                .similarityThreshold(0.5)
                .topK(3)
                .build();

        List<Document> documents =
                vectorStore.similaritySearch(searchRequest);

        // 2. Build context from retrieved chunks
        String context = documents.stream()
                .map(Document::getText)
                .collect(Collectors.joining("\n\n"));

        // 3. Build prompt
        String prompt = """
                You are DevAssist AI, an assistant that answers
                questions using the provided company documents.

                Use only the information provided in the context.
                If the answer is not available in the context,
                say that you don't have enough information.

                Context:
                %s

                Question:
                %s
                """.formatted(context, question);

        // 4. Ask the LLM
        return chatClient
                .prompt()
                .user(prompt)
                .call()
                .content();
    }
}
