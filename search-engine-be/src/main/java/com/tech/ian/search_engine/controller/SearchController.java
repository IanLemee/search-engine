package com.tech.ian.search_engine.controller;

import com.tech.ian.search_engine.service.SearchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/search")
@RestController
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addWord(@RequestBody BodyDto prefix) {
        searchService.add(prefix.prefix());
    }

    @GetMapping("/{prefix}")
    public ResponseEntity<List<String>> search(@PathVariable String prefix) {
        List<String> list = searchService.search(prefix);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
