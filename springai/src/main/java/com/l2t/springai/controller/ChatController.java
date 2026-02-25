package com.l2t.springai.controller;

import com.l2t.springai.config.ChatClientFactory;
import com.l2t.springai.config.LlmProvider;
import org.springframework.ai.anthropic.AnthropicChatOptions;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.core.ParameterizedTypeReference;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ai")
public class ChatController {
    @Autowired
    private ChatClientFactory chatClientFactory;

    @GetMapping("/chat")
    public String chat(@RequestParam String message, @RequestParam(defaultValue = "OPENAI") LlmProvider llmProvider){
        ChatClient chatClient = chatClientFactory.getChatClient(llmProvider);
        return chatClient
                .prompt()
                .user(message)
                .call()
                .content();
    }
    @GetMapping("/state-city-names")
    public List<StateCityNames> getStateCityNamesOfIndia(){
        ChatClient chatClient = chatClientFactory.getChatClient(LlmProvider.ANTHROPIC);
        return chatClient
                .prompt()
                .advisors(new SimpleLoggerAdvisor())
                .options(AnthropicChatOptions.builder().maxTokens(8192).build())
                .system("You are a geography expert. Return structured data about Indian states and their major cities.")
                .user("List all states of India with their top 3 major city names.")
                .call()
                .entity(new ParameterizedTypeReference<List<StateCityNames>>() {});
    }

}
