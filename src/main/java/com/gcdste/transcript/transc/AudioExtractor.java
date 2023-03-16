package com.gcdste.transcript.transc;

import java.io.IOException;

public class AudioExtractor {

    public  String extractAudio(String videoFilePath,String outputPath)throws IOException  {

        String[] command = { "ffmpeg", "-i", videoFilePath, outputPath };

        ProcessBuilder pb = new ProcessBuilder(command);
        pb.start();
        return outputPath;
    }

}