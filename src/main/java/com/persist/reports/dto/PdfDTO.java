package com.persist.reports.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PdfDTO {
    private String fileName;
    private String htmlTemplate;
    private boolean hasHeader;
    private String projectName;
    private String base64Image;
}
