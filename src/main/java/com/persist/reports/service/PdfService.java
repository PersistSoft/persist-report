package com.persist.report.service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.persist.report.dto.PdfDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

@Slf4j
@Service
public class PdfService {

    /**
     * Greate pdf from html template
     * @param pdfDTO
     * @return
     */
    public byte[] createPdf (PdfDTO pdfDTO){
        try {
            Document document = new Document(PageSize.A4);
            document.setMargins(45, 45, 50, 50);
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            PdfWriter writer = PdfWriter.getInstance(document, out);
            document.open();
            document.addAuthor("Persist");
            document.addCreator("Persists - creator");
            document.addSubject("Acta de entrega");
            document.addCreationDate();
            document.addTitle("Acta de entrega");

            this.convertImage(pdfDTO);

            String template = pdfDTO.isHasHeader() ? getHeader(pdfDTO.getProjectName()).concat(pdfDTO.getHtmlTemplate()) : pdfDTO.getHtmlTemplate();

            XMLWorkerHelper.getInstance()
                    .parseXHtml(writer, document, new StringReader(template));
            document.close();

            return out.toByteArray();
        }catch (DocumentException ex) {
            log.error(ex.getMessage(), ex);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return null;
    }

    private void convertImage(PdfDTO pdfDTO){
        try {
            if(!pdfDTO.isHasHeader()){
                return;
            }

            String images [] = pdfDTO.getBase64Image().split(",");

            if(images.length == 0 ){
                return;
            }

            byte[] decodedBytes = Base64.getDecoder().decode(images[1]);
            Files.write(Paths.get(String.format("/tmp/%s.jpg", pdfDTO.getProjectName())), decodedBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getHeader(String name){
        return String.format("<p align='left'>" +
                "<img width='300' src='http://localhost:8082/api-report/v1/images/%s' alt='%s'/>" +
                "</p>", name, name);
    }

}
