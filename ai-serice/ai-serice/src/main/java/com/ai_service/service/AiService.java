package com.ai_service.service;


import com.ai_service.client.DocumentServiceClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AiService {

    private ChatClient chatClient;
    private  DocumentServiceClient documentServiceClient;
    private PdfReaderService pdfReaderService;


    public AiService(ChatClient.Builder builder,DocumentServiceClient client,PdfReaderService pdfSer) {
        this.chatClient = builder.build();
        this.documentServiceClient = client;
        this.pdfReaderService = pdfSer;
    }

    public String chat(String question) {

        return chatClient
                .prompt()
                .user(question)
                .call()
                .content();
    }


    public String testDocumentDownload(Long documentId,String auth) {
        byte[] bytes = documentServiceClient.downloadDocument(documentId,auth);
        if (bytes == null) {
            return "Document download failed";
        }
        log.info("Download successfull.....");
        //pdfReaderService.processPdf(bytes,documentId);

        return "PDF read successfully: ";
    }
}
