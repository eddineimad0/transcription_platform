package com.gcdste.transcript.transc;

import com.sapher.youtubedl.YoutubeDLException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
@RestController
@RequestMapping("/transcribe")
public class TranscriptionController {

    @GetMapping
    public String transcribe(@RequestParam(name="url")String url){
        // Supprimer les espace;
        System.out.println("[LOG] Request received: "+url);
        url = url.trim();
        if(url.isEmpty()) {
            System.out.println("[LOG]: Url empty.");
            return "Error: The submitted Url is empty";
        }
        // Assurer qu'il s'agit bien d'un URL youtube
        String regexString ="https:\\/\\/www\\.youtube\\.com\\/watch\\?v=[a-zA-Z0-9+_=]+&*.*";
        Pattern urlPattren = Pattern.compile(regexString);
        Matcher m = urlPattren.matcher(url);
        if(!m.matches())
        {
            System.out.println("[LOG]: Url value failed the regex test.");
            return "Error: Please submit a Youtube video url.";
        }

        int start = url.indexOf('=') + 1;
        int end = url.length();
        if (url.contains("&")){
            end = url.indexOf('&');
        }
        String videoId = url.substring(start,end).trim();
        String videoPath = PathBuilder.getVideoDirPath() + videoId + ".webm";
        String audioPath = PathBuilder.getAudioDirPath() + videoId + ".wav";
        String outputPath = PathBuilder.getTextDirPath() + videoId + ".txt";
        File f = new File(outputPath);
        if(f.exists() && !f.isDirectory()) {
            // file already exists.
            return videoId;
        }

        //1) Youtube Download.
        String outputDir = PathBuilder.getVideoDirPath();
        VideoDownloader vdd = new VideoDownloader(outputDir);
        try {
            vdd.getVideo(url);
        }catch (YoutubeDLException error){
            System.out.println("[ERROR]: Couldn't Download the video,"+error.getMessage());
            return "Error: Couldn't Download the video.";
        }
        //2) FFmepg extract audio.

        AudioExtractor audioEx = new AudioExtractor();
        try{
            audioEx.extractAudio(videoPath,audioPath);
        }catch (IOException error){
            System.out.println("[ERROR]: Couldn't extract audio from video,"+error.getMessage());
            return "Error: Couldn't extract the audio.";
        }
        //3) Speechmatics api
        boolean success = SpeechmaticsApi.ToTxt(audioPath,outputPath);
        if(!success){
            return "Error: Failed to process the video.";
        }
        //5) respond to user
        System.out.println("[LOG] Request Done: "+url);
        return videoId;

    }
}
