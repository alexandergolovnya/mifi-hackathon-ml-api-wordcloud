package com.example.demo;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class TextParserService {

    private final RestTemplate restTemplate;

    @Autowired
    public TextParserService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    List<TagEntity> getTagEntities() throws IOException, JSONException {
        File file = new File("src/main/resources/input.txt");

        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuilder builder = new StringBuilder();
        String currentLine = reader.readLine();

        while (currentLine != null) {
            currentLine = currentLine.toLowerCase();
            currentLine = currentLine.replaceAll("(\\s+|!|\\|\"|,|\\.|\\?|:|;|\\)|\\(|\\\\|/|%|\\d+|«|»|\\*|\"|\\+|-)", " ");
            currentLine = currentLine.replaceAll("-", " ");
            builder.append(currentLine).append(" ");
            currentLine = reader.readLine();
        }

        reader.close();
        String inputString = builder.toString();

        final String url = "http://127.0.0.1:5000/tags";
        HttpHeaders headers = new HttpHeaders();
        MediaType mediaType = new MediaType("application", "json", StandardCharsets.UTF_8);
        headers.setContentType(mediaType);

        HttpEntity<String> requestEntity = new HttpEntity<>(inputString, headers);
        String morphMapEntityList = restTemplate.postForObject(url, requestEntity, String.class);
        JSONObject json = new JSONObject(morphMapEntityList);
        JSONObject jsonObject = json.getJSONObject("tags");
        String str = jsonObject.toString();
        List<TagEntity> tagEntityList = new ArrayList<>();
        String[] stringList = str.replaceAll("([{}])","").split(",");
        for(String a : stringList){
            List<String> b = Arrays.asList(a.split(":"));
            TagEntity tagEntity = new TagEntity();
            tagEntity.setWord(b.get(0).replaceAll("\"",""));
            tagEntity.setWeight((Integer.parseInt(b.get(1)) / 3));
            if (tagEntity.getWord().contains("росат")) tagEntity.setWeight((Integer.parseInt(b.get(1)) / 6));
            tagEntityList.add(tagEntity);
        }

        return tagEntityList;
    }

    String getString() throws IOException {
        File file = new File("src/main/resources/input.txt");

        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuilder builder = new StringBuilder();
        String currentLine = reader.readLine();

        while (currentLine != null) {
            currentLine = currentLine.toLowerCase();
            currentLine = currentLine.replaceAll("(\\s+|!|\\|\"|,|\\.|\\?|:|;|\\)|\\(|\\\\|/|%|\\d+|«|»|\\*|\"|\\+|-)", " ");
            currentLine = currentLine.replaceAll("-", " ");
            builder.append(currentLine).append(" ");
            currentLine = reader.readLine();
        }

        reader.close();
        String inputString = builder.toString();
        return inputString;
    }
}
