package com.example.bookmanager.Controller;

import com.example.bookmanager.DTO.ChatRequest;
import com.example.bookmanager.Exception.BusinessException;
import com.example.bookmanager.Service.OllamaService;
import com.example.bookmanager.Utils.Result;
import com.example.bookmanager.Utils.ResultData;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/chat")
public class OllamaController {
    private final OllamaService ollamaService;

    public OllamaController(OllamaService ollamaService) {
        this.ollamaService = ollamaService;
    }

    @PostMapping
    public Result chat(@RequestBody @Valid ChatRequest chatRequest) {
        try {
            return ResultData.success("Chat successfully", ollamaService.generateText(chatRequest));
        } catch (Exception e) {
            throw new BusinessException(4, 400, "Failed to chat: " + e.getMessage());
        }
    }

    @PostMapping("/stream")
    public Flux<String> streamChat(@RequestBody @Valid ChatRequest chatRequest) {
        return ollamaService.streamText(chatRequest);
    }
}
