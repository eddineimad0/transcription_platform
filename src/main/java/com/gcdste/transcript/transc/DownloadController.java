package com.gcdste.transcript.transc;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.core.io.Resource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@RestController
public class DownloadController {
    @GetMapping(path = "/download/{videoId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String videoId) {
        String filePath = PathBuilder.getTextDirPath() + videoId + ".txt";
        File downloadFile = new File(filePath);
        InputStreamResource resource;
        try{
            resource = new InputStreamResource(new FileInputStream(downloadFile));
        }catch (FileNotFoundException e){
            return ResponseEntity
                    .notFound()
                    .build();
        }
        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + downloadFile.getName());
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");
        return ResponseEntity.ok()
                .headers(header)
                .contentLength(downloadFile.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }
}
