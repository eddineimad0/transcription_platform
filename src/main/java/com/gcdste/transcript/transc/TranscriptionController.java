package com.gcdste.transcript.transc;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/transcribe")
public class TranscriptionController {
    @GetMapping
    public String transcribe(@RequestParam(name="url")String url){
        return url;
    }
}
