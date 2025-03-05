package com.example.bookmanager.Service;

import com.example.bookmanager.DTO.ChatRequest;
import com.example.bookmanager.Exception.BusinessException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class OllamaService {
    private final RestTemplate restTemplate;
    private final String apiUrl;
    private final String systemPrompt;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public OllamaService(RestTemplate restTemplate, @Value("${ollama.api.url}") String apiUrl, @Value("${ollama.system-prompt}") String systemPrompt) {
        this.restTemplate = restTemplate;
        this.apiUrl = apiUrl;
        this.systemPrompt = systemPrompt;
    }

    public Map<String, Object> chat(ChatRequest request) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", request.getModel());
        requestBody.put("stream", request.isStream());
        List<Map<String, String>> messages = new ArrayList<>();
        messages.add(Map.of("role", "user", "content", request.getPrompt()));
        messages.add(Map.of("role", "system", "content", systemPrompt));
        requestBody.put("messages", messages);
        System.out.println(requestBody);
        ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, requestBody, String.class);
        try {
            return objectMapper.readValue(response.getBody(), new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new BusinessException(4, 500, "Server error");
        }
    }
}