package com.ai_service.controller;

import com.ai_service.service.PdfReaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ai")
public class PDFReadController {

    private final PdfReaderService pdfReaderService;

    /**
     * This API will read the documents.
     * @return response
     */
    @GetMapping("/read")
    public ResponseEntity<String> readPdf(){
        pdfReaderService.readPdf();
        return ResponseEntity.ok("Successfully read the PDF");
    }

    /**
     * This API handles the upload of document from the document service
     * @param resource holds the uploaded file
     * @return response
     */
    @PostMapping("/processPDF")
    public ResponseEntity<String> process(@RequestBody byte[] resource){
        pdfReaderService.processPdf(resource);
        return ResponseEntity.ok("Successfully saved the document in vector store");
    }
}
