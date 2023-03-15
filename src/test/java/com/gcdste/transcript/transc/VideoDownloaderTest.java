package com.gcdste.transcript.transc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VideoDownloaderTest {
    @Test
    public void shouldDownloadVideo(){
        VideoDownloader vdd = new VideoDownloader("E:\\Code\\Java\\transc\\src\\main\\resources\\videos\\");
        String path = vdd.getVideo("https://www.youtube.com/watch?v=7d_jQycdQGo");
        System.out.println(path);
        assertNotEquals(path,"");
    }
}