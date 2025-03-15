package com.persist.reports.rest;

import com.persist.reports.rest.commons.ApiConst;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Slf4j
@CrossOrigin()
@RestController
@RequiredArgsConstructor
@RequestMapping(ApiConst.API_IMAGES)
public class ImageController {
    @GetMapping("/{imageName}")
    void createPdf(@PathVariable String imageName, HttpServletResponse response) {
        String imagePath = String.format("/tmp/%s.jpg", imageName);
        File file = new File(imagePath);

        if (!file.exists()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        response.setContentType("image/jpeg");
        response.setHeader("Content-Disposition", "inline; filename=\"" + imageName + "\"");
        response.setContentLengthLong(file.length());

        try (InputStream inputStream = new FileInputStream(file);
             OutputStream outputStream = response.getOutputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.flush();
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            log.error("Error: {}", e.getMessage());
        }
    }
}
