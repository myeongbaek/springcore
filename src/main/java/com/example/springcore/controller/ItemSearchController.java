package com.example.springcore.controller;


import com.example.springcore.dto.ItemDto;
import com.example.springcore.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
