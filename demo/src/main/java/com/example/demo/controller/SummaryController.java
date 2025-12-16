package com.example.demo.controller;

import com.example.demo.service.SummaryService;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/summaries")
public class SummaryController {

    private final SummaryService summaryService;

    public SummaryController(SummaryService summaryService) {
        this.summaryService = summaryService;
    }

    @PostMapping
    public Map<String, String> summarize(@RequestBody Map<String, String> body) {
        String text = body.getOrDefault("text", "");
        String summary = summaryService.summarize(text);
        return Map.of("summary", summary);
    }
}

