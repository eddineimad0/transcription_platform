package com.gcdste.transcript.transc;

import com.sapher.youtubedl.YoutubeDLException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
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
            return "";
        }
        // Assurer qu'il s'agit bien d'un URL youtube
        String regexString ="https:\\/\\/www\\.youtube\\.com\\/watch\\?v=[a-zA-Z0-9+_=]+";
        Pattern urlPattren = Pattern.compile(regexString);
        Matcher m = urlPattren.matcher(url);
        if(!m.matches())
        {
            System.out.println("[LOG]: Url value failed the regex test.");
            return "";
        }



        //1) Youtube Download.
        String outputDir = PathBuilder.getVideoDirPath();
        VideoDownloader vdd = new VideoDownloader(outputDir);
        try {
            vdd.getVideo(url);
        }catch (YoutubeDLException error){
            System.out.println("[ERROR]: Couldn't Download the video,"+error.getMessage());
            return "";
        }
        //2) FFmepg extract audio.
        String videoId = url.substring(url.indexOf('=')+1,url.length()).trim();
        String videoPath = PathBuilder.getVideoDirPath() + videoId + ".webm";
        String audioPath = PathBuilder.getAudioDirPath() + videoId + ".wav";
        String outputPat = PathBuilder.getAudioDirPath() + videoId + ".txt";

        AudioExtractor audioEx = new AudioExtractor();
        try{
            audioEx.extractAudio(videoPath,audioPath);
        }catch (IOException error){
            System.out.println("[ERROR]: Couldn't extract audio from video,"+error.getMessage());
        }
        //3) Speechmatich api

        SpeechmaticsApi.ToTxt(audioPath,outputPat);

        //4) write to file
        //5) respond to user
        return url;
    }
}
