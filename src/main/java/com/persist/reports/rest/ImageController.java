package com.persist.report.rest;

import com.persist.report.rest.commons.ApiConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

@Slf4j
@CrossOrigin()
@RestController
@RequiredArgsConstructor
@RequestMapping(ApiConst.API_IMAGES)
public class ImageController {
    @GetMapping("/{imageName}")
    void createPdf(@PathVariable String imageName, HttpServletResponse response){
        try {
            String imagePath = String.format("/tmp/%s.jpg", imageName);
            File initialFile = new File(imagePath);
            InputStream targetStream = new FileInputStream(initialFile);

            MediaType mediaType = MediaType.IMAGE_PNG;
            response.setContentType(mediaType.toString());
            response.setHeader("Content-Disposition", "inline; filename=\"" + imageName + "\"");
            response.getOutputStream().write(targetStream.readAllBytes());

        } catch (Exception e) {
            System.out.print(e.getMessage());
            e.printStackTrace();
        }
    }
}
