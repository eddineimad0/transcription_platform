package com.gcdste.transcript.transc;

import com.sapher.youtubedl.YoutubeDLException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VideoDownloaderTest {
    @Test
    public void shouldDownloadVideo(){
        String videoDir = PathBuilder.buildAbsolutePath( "\\src\\main\\resources\\videos\\");
        VideoDownloader vdd = new VideoDownloader(videoDir);
        try{
            String path = vdd.getVideo("https://www.youtube.com/watch?v=7d_jQycdQGo");
            assertNotEquals(path,"");
        }catch (YoutubeDLException error){
            System.out.println("[ERROR]: Couldn't download the video,"+error.getMessage());
        }
    }
}