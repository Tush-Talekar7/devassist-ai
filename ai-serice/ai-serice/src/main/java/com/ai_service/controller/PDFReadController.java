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

    @GetMapping("/read")
    public ResponseEntity<String> readPdf(){
        pdfReaderService.readPdf();
        return ResponseEntity.ok("Successfully read the PDF");
    }

    @PostMapping("/processPDF")
    public ResponseEntity<String> process(@RequestBody byte[] resource){
        pdfReaderService.processPdf(resource);
        return ResponseEntity.ok("Successfully saved the document in vector store");
    }
}
