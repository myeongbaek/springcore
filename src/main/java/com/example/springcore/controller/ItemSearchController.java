package com.example.springcore.controller;

import com.example.springcore.dto.ItemDto;
<<<<<<< HEAD
import com.example.springcore.service.SearchService;
=======
>>>>>>> origin/master
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
<<<<<<< HEAD
import lombok.RequiredArgsConstructor;
=======
>>>>>>> origin/master
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class ItemSearchController {

    private final SearchService searchService;

    @GetMapping("/api/search")
    @ResponseBody
    public List<ItemDto> getItems(@RequestParam String query) throws IOException {
        return searchService.searchItems(query);
    }
}
