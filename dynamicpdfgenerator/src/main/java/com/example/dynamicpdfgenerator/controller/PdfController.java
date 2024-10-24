package com.example.dynamicpdfgenerator.controller;

import com.example.dynamicpdfgenerator.model.ErrorResponse;
import com.example.dynamicpdfgenerator.model.InvoiceRequest;
import com.example.dynamicpdfgenerator.service.PdfService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RestController
@RequestMapping("/api/pdf")
public class PdfController {

    private final PdfService pdfService;

    public PdfController(PdfService pdfService) {
        this.pdfService = pdfService;
    }

    @PostMapping(value = "/generate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> generatePdf(@RequestBody InvoiceRequest request) {
        try {
            String pdfFilePath = pdfService.generatePdf(request);
            File pdfFile = new File(pdfFilePath);
            return ResponseEntity.ok().body("PDF generated successfully: " + pdfFile.getPath());
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error generating PDF: " + e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Unexpected error: " + e.getMessage()));
        }
    }
}
