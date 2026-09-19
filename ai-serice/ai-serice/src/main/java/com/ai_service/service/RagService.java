package com.ai_service.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RagService {

    private  VectorStore vectorStore;
    private  ChatClient chatClient;

    public RagService(VectorStore vectorStore1,ChatClient.Builder client){
        this.chatClient = client.build();
        this.vectorStore = vectorStore1;
    }

    /**
     * This method is used to retrieve relevant text from the vector and send it to the LLM,
     * LLM gives the answer
     * @param question holds the user question
     * @return answer from the LLM
     */
    public String answerQuestion(String question) {
        try {
            // 1. Retrieve relevant chunks
            log.info("Retrieving the related chunks from the vector store .. ");
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
        } catch (RuntimeException e) {
            log.error("Exception occurred while answering the question {}", question);
            throw new RuntimeException(e);
        }
    }
}
