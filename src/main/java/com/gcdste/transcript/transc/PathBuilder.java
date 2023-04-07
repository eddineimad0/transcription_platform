package com.gcdste.transcript.transc;

import java.io.File;

public class PathBuilder {
    public static String buildAbsolutePath(String relative){
        String baseDir = new File("").getAbsolutePath();
        return baseDir + relative;
    }

    public static String getVideoDirPath(){
        return buildAbsolutePath("\\src\\main\\resources\\videos\\");
    }

    public  static String getAudioDirPath(){
        return  buildAbsolutePath("\\src\\main\\resources\\audios\\");
    }

    public  static String getTextDirPath(){
        return  buildAbsolutePath("\\src\\main\\resources\\Texts\\");
    }
}
