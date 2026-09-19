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

    /**
     * Testing the download of the document from the document service.
     * @param documentId it is the document id
     * @param auth it will hold the authorization key that is jwt key
     * @return response
     */
    public String testDocumentDownload(Long documentId,String auth) {
        byte[] bytes = documentServiceClient.downloadDocument(documentId,auth);
        if (bytes == null) {
            return "Document download failed";
        }
        log.info("Download successfully.....");
        //pdfReaderService.processPdf(bytes,documentId);

        return "PDF read successfully: ";
    }
}
