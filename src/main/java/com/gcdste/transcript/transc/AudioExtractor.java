package com.gcdste.transcript.transc;

import java.io.IOException;

public class AudioExtractor {

    public  String extractAudio(String videoFilePath,String outputPath)throws IOException  {
        String ffmpeg_path = PathBuilder.buildAbsolutePath("/deps/bins/ffmpeg");
        String[] command = { ffmpeg_path, "-i", videoFilePath, outputPath };

        ProcessBuilder pb = new ProcessBuilder(command);
        pb.start();
        return outputPath;
    }

}