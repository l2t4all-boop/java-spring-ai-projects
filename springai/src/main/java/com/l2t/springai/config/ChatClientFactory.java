package com.l2t.springai.config;

import java.util.EnumMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.anthropic.AnthropicChatModel;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.google.genai.GoogleGenAiChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.stereotype.Component;

@Component
public class ChatClientFactory {

    private static final Logger log = LoggerFactory.getLogger(ChatClientFactory.class);

    private final Map<LlmProvider, ChatClient> clients;
    private final Map<LlmProvider, ChatModel> models;

    public ChatClientFactory(AnthropicChatModel anthropicChatModel,
                             OpenAiChatModel openAiChatModel,
                             GoogleGenAiChatModel googleGenAiChatModel) {
        clients = new EnumMap<>(LlmProvider.class);
        clients.put(LlmProvider.ANTHROPIC, ChatClient.create(anthropicChatModel));
        clients.put(LlmProvider.OPENAI, ChatClient.create(openAiChatModel));
        clients.put(LlmProvider.GOOGLE, ChatClient.create(googleGenAiChatModel));

        models = new EnumMap<>(LlmProvider.class);
        models.put(LlmProvider.ANTHROPIC, anthropicChatModel);
        models.put(LlmProvider.OPENAI, openAiChatModel);
        models.put(LlmProvider.GOOGLE, googleGenAiChatModel);

        log.info("ChatClientFactory initialized with providers: {}", clients.keySet());
    }

    public ChatClient getChatClient(LlmProvider provider) {
        return clients.get(provider);
    }

    public ChatModel getChatModel(LlmProvider provider) {
        return models.get(provider);
    }

    public void logProviderDetails(LlmProvider provider) {
        ChatModel chatModel = models.get(provider);
        ChatOptions options = chatModel.getDefaultOptions();
        log.info("Provider: {} | Model: {} | Class: {} | Temperature: {} | MaxTokens: {} | TopP: {} | TopK: {}",
                provider, options.getModel(), chatModel.getClass().getSimpleName(),
                options.getTemperature(), options.getMaxTokens(), options.getTopP(), options.getTopK());
    }
}
