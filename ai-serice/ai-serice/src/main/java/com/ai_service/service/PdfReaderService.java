package com.ai_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PdfReaderService {

    private final VectorStoreService vectorStoreService;

    /**
     * This method will readPDF from the tesdocs folder. It is a testing method.
     */
    public void readPdf(){
        PagePdfDocumentReader pdfDocumentReader = new PagePdfDocumentReader("classpath:/testdocs/test.pdf");

        List<Document> documentList = pdfDocumentReader.read();

        System.out.println("Total Pages " + documentList.size());

        for(Document document : documentList){
            System.out.println("-----");
            System.out.println("Metadata: " + document.getMetadata());
            System.out.println("Text: " + document.getText());
        }
    }

    /**
     * This method will make the chunks of the pdf and save it in vector DB.
     * @param pdfBytes holds the PDF in bytes
     */
    public void processPdf(byte[] pdfBytes) {
        try {
            log.info("---------Started processing the document ");
            Resource pdfResource = new ByteArrayResource(pdfBytes);
            PagePdfDocumentReader pdfReader = new PagePdfDocumentReader(pdfResource);
            log.info("Started converting the pdf into document ");
            List<Document> documents = pdfReader.read();
            vectorStoreService.addDocuments(documents);
            System.out.println("Pages/Documents extracted: " + documents.size());
        }catch (RuntimeException e){
            log.error("Exception occurred {}",e);
        }
    }
}
