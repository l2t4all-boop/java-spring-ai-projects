package com.l2t.ipagent.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.l2t.ipagent.domain.InterviewQuestions;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class IPrepAIService {

    private final ChatClient chatClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public IPrepAIService(ChatClient.Builder builder){
        this.chatClient = builder.build();
    }

    public Map<String, List<String>> getCourseAndTopics(){
        try (InputStream is = new ClassPathResource("course_topic_data.json").getInputStream()) {
            JsonNode root = objectMapper.readTree(is);
            JsonNode courses = root.get("courses");
            Map<String, List<String>> courseTopics = new LinkedHashMap<>();
            for (JsonNode course : courses) {
                String courseName = course.get("course").asText();
                List<String> topics = objectMapper.convertValue(
                        course.get("topics"), new TypeReference<List<String>>() {});
                courseTopics.put(courseName, topics);
            }
            return courseTopics;
        } catch (IOException e) {
            throw new RuntimeException("Failed to read course_topic_data.json", e);
        }
    }

    public InterviewQuestions getInterviewQuestions(String courseName, String topicName){
        String systemPrompt = """
                You are an expert interview coach specializing in technical interviews.
                Generate interview preparation material for the given course and topic.
                Provide a mix of theoretical questions with answers and multiple-choice quiz questions.
                Return the response as JSON with the following structure:
                {
                  "questions": [
                    { "question": "...", "answer": "...", "example": "A real-world scenario or use case", "exampleWithCode": "A code snippet demonstrating the concept with explanation" }
                  ],
                  "quizQuestions": [
                    { "question": "...", "options": ["A. ...", "B. ...", "C. ...", "D. ..."], "correctOption": "A. ..." }
                  ]
                }
                Generate 5 interview questions with detailed answers (each answer must be at least 50 words) and 5 quiz questions with 4 options each.
                For each interview question, also include a real-world example and a code example with explanation.
                """;

        String userPrompt = "Generate interview questions for the course: " + courseName
                + " on the topic: " + topicName;

        return chatClient.prompt()
                .system(systemPrompt)
                .advisors(new SimpleLoggerAdvisor())
                .user(userPrompt)
                .call()
                .entity(InterviewQuestions.class);
    }
}
