package com.persist.reports.rest;

import com.persist.reports.dto.PdfDTO;
import com.persist.reports.rest.commons.ApiConst;
import com.persist.reports.service.PdfService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin()
@RestController
@RequiredArgsConstructor
@RequestMapping()
public class PdfController {
    private final PdfService pdfService;

    @PostMapping(ApiConst.API_PDF)
    ResponseEntity<byte[]> createPdf(@RequestBody PdfDTO pdfDTO){
        byte[] bis = pdfService.createPdf(pdfDTO);

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF)
                .body(bis);
    }
}
