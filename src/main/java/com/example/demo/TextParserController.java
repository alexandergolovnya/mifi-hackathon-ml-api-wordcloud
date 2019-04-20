package com.example.demo;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/parser")
public class TextParserController {

    private final TextParserService textParserService;

    @Autowired
    public TextParserController(TextParserService textParserService) {
        this.textParserService = textParserService;
    }

    @GetMapping("/getTags")
    public  List<TagEntity> getTags() throws IOException, JSONException {
//        List<TagEntity> tagEntities = textParserService.getTagEntities();
//        Map<String, Integer> tagMap = tagEntities.stream().collect(
//                Collectors.toMap(TagEntity::getWord, TagEntity::getWeight));

        return textParserService.getTagEntities();
    }

    @GetMapping("/getString")
    public  String getString() throws IOException, JSONException {
//        List<TagEntity> tagEntities = textParserService.getTagEntities();
//        Map<String, Integer> tagMap = tagEntities.stream().collect(
//                Collectors.toMap(TagEntity::getWord, TagEntity::getWeight));

        return textParserService.getString();
    }
}
