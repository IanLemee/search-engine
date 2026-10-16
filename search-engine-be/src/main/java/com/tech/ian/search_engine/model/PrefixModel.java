package com.tech.ian.search_engine.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Document(value = "PrefixTable")
public class PrefixModel {
    @Id
    private String id;
    private List<Map<String, Integer>> map;

    public PrefixModel() {
    }

    public PrefixModel(String id, List<Map<String, Integer>> map) {
        this.id = id;
        this.map = map;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Map<String, Integer>> getMap() {
        return map;
    }

    public void setMap(List<Map<String, Integer>> map) {
        this.map = map;
    }
}
