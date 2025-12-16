package com.example.demo.service;

import org.springframework.stereotype.Service;
import java.util.Arrays;

@Service
public class SummaryService {

    public String summarize(String text) {
        if (text == null || text.isBlank()) {
            return "No content provided.";
        }

        String[] words = text.split("\\s+");
        int limit = Math.min(words.length, 70);

        return String.join(" ", Arrays.copyOfRange(words, 0, limit));
    }
}

