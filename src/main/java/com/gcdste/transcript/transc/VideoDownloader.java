package com.gcdste.transcript.transc;

import com.sapher.youtubedl.YoutubeDLException;
import com.sapher.youtubedl.YoutubeDLRequest;
import com.sapher.youtubedl.YoutubeDLResponse;
import com.sapher.youtubedl.YoutubeDL;

public class VideoDownloader {

    private final String destDir;

    VideoDownloader(String destDir){
        this.destDir = destDir;
    }
    public String getVideo(String videoUrl){

        YoutubeDLRequest request = new YoutubeDLRequest(videoUrl, destDir);
        request.setOption("ignore-errors");		// --ignore-errors
        request.setOption("output", "%(id)s");	// --output "%(id)s"
        request.setOption("retries", 10);		// --retries 10

        try{
            YoutubeDLResponse response = YoutubeDL.execute(request);
            String stdOut = response.getOut(); // Executable output;
            System.out.println(stdOut);
            return stdOut;
        }catch (YoutubeDLException error){
            return "";
        }
    }
}
