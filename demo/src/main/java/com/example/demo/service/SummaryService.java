





package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Map;

@Service
public class SummaryService {

    
    private static final String OLLAMA_URL = "http://localhost:11434/api/generate";
    private static final String MODEL_NAME = "llama3";

    
    private static final double COMPRESSION_RATIO = 0.60;

    private final RestTemplate restTemplate = new RestTemplate();

    public String summarize(String text) {
        if (text == null || text.isBlank()) {
            return "No content provided.";
        }

       
        String[] inputWords = text.trim().split("\\s+");
        int inputCount = inputWords.length;

        
        int maxWords = Math.max(1, (int) Math.floor(inputCount * COMPRESSION_RATIO));

        String prompt =
            "Reword and shorten the following text. " +
            "Return a clearly shorter version that is no more than " + maxWords + " words. " +
            "Do not copy long phrases verbatim; rephrase. " +
            "Return only the rewritten text, no headings.\n\n" +
            text;

        Map<String, Object> requestBody = Map.of(
            "model", MODEL_NAME,
            "prompt", prompt,
            "stream", false
        );

        try {
            @SuppressWarnings("rawtypes")
            Map response = restTemplate.postForObject(OLLAMA_URL, requestBody, Map.class);

            if (response == null || response.get("response") == null) {
                return "No summary generated.";
            }

            String summary = response.get("response").toString().trim();

            
            String[] summaryWords = summary.split("\\s+");
            if (summaryWords.length > maxWords) {
                summary = String.join(" ", Arrays.copyOfRange(summaryWords, 0, maxWords));
            }

            return summary;

        } catch (RestClientException e) {
            
            return "LLM service unavailable. Make sure Ollama is running and the model is installed.";
        }
    }
}
