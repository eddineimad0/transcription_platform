package com.gcdste.transcript.transc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.regex.Pattern;
import java.util.regex.Matcher;


@RestController
@RequestMapping("/transcribe")
public class TranscriptionController {

    @GetMapping
    public String transcribe(@RequestParam(name="url")String url){
        // Supprimer les espace;
        url = url.trim();
        if(url.isEmpty()) {
            System.out.println("[LOG]: Url empty.");
        }
        // Assurer qu'il s'agit bien d'un URL
        String regexString ="((http|https)://)(www.)?"+
                "[a-zA-Z0-9@:%._\\+~#?&//=]{2,256}\\.[a-z]"+
                "{2,6}\\b([-a-zA-Z0-9@:%._\\+~#?&//=]*)";
        Pattern urlPattren = Pattern.compile(regexString);
        Matcher m = urlPattren.matcher(url);
        if(!m.matches())
        {
            System.out.println("[LOG]: Url value failed the regex test.");
        }

        //1) Youtube Download.
        VideoDownloader vdd = new VideoDownloader("E:\\Code\\Java\\transc\\src\\main\\resources\\videos");
        vdd.getVideo(url);
        //2) FFmepg  extract audio.
        //3) Speechmatich api
        //4) write to file
        //5) respond to user
        return url;
    }
}
