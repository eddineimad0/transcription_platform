package com.gcdste.transcript.transc;

import java.io.*;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.json.*;
public class SpeechmaticsApi {
    public static boolean ToTxt(String audioPath ,String outputPath) {
        String url = "https://asr.api.speechmatics.com/v2/jobs/";
        String speechApiKey = PropertiesReader.getProperty("SPEECH_API_KEY");
        String requestBody = "{\"type\": \"transcription\",\"transcription_config\": { \"operating_point\":\"enhanced\", \"language\": \"en\" }}";
        File audioFile = new File(audioPath);

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(url);

        // Ajouter la clé API à l'en-tête de la requête
        httpPost.addHeader(new BasicHeader("Authorization", "Bearer " + speechApiKey));

        // Créer le corps de la requête avec un fichier MP3
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        builder.addBinaryBody("data_file", audioFile, ContentType.DEFAULT_BINARY, audioFile.getName());
        builder.addTextBody("config", requestBody, ContentType.APPLICATION_JSON);
        HttpEntity requestEntity = builder.build();
        httpPost.setEntity(requestEntity);

        try {
            HttpEntity responseEntity = httpClient.execute(httpPost).getEntity();
            String responseBody = responseEntity != null ? EntityUtils.toString(responseEntity) : null;
            JSONObject jsonObject = new JSONObject(responseBody);
            String jobId = jsonObject.getString("id");
            boolean done = false;
            while (!done) {
                Thread.sleep(5000); // Attendez 5 secondes avant de vérifier l'état du travail de transcription
                HttpGet getRequest = new HttpGet(url + jobId);
                getRequest.addHeader("Authorization", "Bearer " + speechApiKey);
                HttpResponse jobResponse = httpClient.execute(getRequest);
                BufferedReader jobReader = new BufferedReader(new InputStreamReader(jobResponse.getEntity().getContent()));
                StringBuilder jobBuilder = new StringBuilder();
                String jobLine = null;
                while ((jobLine = jobReader.readLine()) != null) {
                    jobBuilder.append(jobLine);
                }
                String jobJsonResponse = jobBuilder.toString();
                JSONObject jobJsonObject = new JSONObject(jobJsonResponse);

                JSONObject job = jobJsonObject.getJSONObject("job");
                String jobStatus = job.getString("status");
                if (jobStatus.equals("done")) {
                    done = true;
                }
            }

            HttpGet getRequest2 = new HttpGet(url + jobId + "/transcript?format=txt");
            getRequest2.addHeader("Authorization", "Bearer " + speechApiKey);
            HttpResponse jobResponse2 = httpClient.execute(getRequest2);
            BufferedReader jobReader2 = new BufferedReader(new InputStreamReader(jobResponse2.getEntity().getContent()));
            StringBuilder jobBuilder2 = new StringBuilder();
            String jobLine2 = null;
            while ((jobLine2 = jobReader2.readLine()) != null) {
                jobBuilder2.append(jobLine2);
            }
            String jobJsonResponse2 = jobBuilder2.toString();
            File file = new File(outputPath);
            file.setWritable(true);
            FileWriter fr = new FileWriter(file);
            fr.write(jobJsonResponse2);
            fr.close();

        } catch (IOException e) {
            return false;
        } catch (InterruptedException e) {
            return false;
        }
        return true;
    }
}
