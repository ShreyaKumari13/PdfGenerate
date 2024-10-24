package com.example.dynamicpdfgenerator.service;

import com.example.dynamicpdfgenerator.model.InvoiceRequest;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class PdfService {
    @Value("${pdf.storage.location}")
    private String pdfStorageLocation;

    public String generatePdf(InvoiceRequest request) throws IOException, DocumentException {
        String pdfFileName = generateFilename() + ".pdf"; // Updated to use the new filename method
        String pdfFilePath = pdfStorageLocation + pdfFileName; // Use full path to store the PDF file
        File pdfFile = new File(pdfFilePath);

        // Create the necessary directories if they don't exist
        pdfFile.getParentFile().mkdirs();

        Document document = new Document(); // Create the Document instance
        FileOutputStream outputStream = null; // Declare the output stream outside

        try {
            outputStream = new FileOutputStream(pdfFile); // Initialize the FileOutputStream
            PdfWriter.getInstance(document, outputStream); // Associate PdfWriter with Document
            document.open(); // Open the Document

            addInvoiceContent(document, request);
        } catch (DocumentException e) {
            throw new DocumentException("Error while generating PDF: " + e.getMessage(), e);
        } catch (IOException e) {
            throw new IOException("File operation error: " + e.getMessage(), e);
        } finally {
            if (document.isOpen()) {
                document.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        }

        return pdfFileName;
    }

    private String generateFilename() {
        long timestamp = System.currentTimeMillis(); // Get the current timestamp
        return "invoice" + timestamp; // Return the filename in the desired format
    }

    private void addInvoiceContent(Document document, InvoiceRequest request) throws DocumentException {
        document.add(new Paragraph("Invoice"));
        document.add(new Paragraph("Seller: " +"\n"+ request.getSeller()));
        document.add(new Paragraph(request.getSellerAddress()));
        document.add(new Paragraph("GSTIN: " + request.getSellerGstin()));

        document.add(new Paragraph("Buyer: " + request.getBuyer()));
        document.add(new Paragraph(request.getBuyerAddress()));
        document.add(new Paragraph("GSTIN: " + request.getBuyerGstin()));

        document.add(new Paragraph("Items:"));

        for (InvoiceRequest.Item item : request.getItems()) {
            document.add(new Paragraph(" - " + item.getName() +
                    " | Quantity: " + item.getQuantity() +
                    " | Rate: " + item.getRate() +
                    " | Amount: " + item.getAmount()));
        }
    }
}
