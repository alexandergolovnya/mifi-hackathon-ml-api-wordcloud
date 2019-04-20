package com.example.demo;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class MorphMapEntity {
    private List<Map<String, Integer>> tags;
}
