package com.l2t.ipagent.controller;

import com.l2t.ipagent.domain.InterviewQuestionInput;
import com.l2t.ipagent.domain.InterviewQuestions;
import com.l2t.ipagent.service.IPrepAIService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/ai")
@RequiredArgsConstructor
public class IPrepAIController {

    private final IPrepAIService iPrepAIService;

    @GetMapping("/course-topics")
    public Map<String, List<String>> getCoursesAndTopics(){
        return iPrepAIService.getCourseAndTopics();
    }

    @PostMapping("/interview-questions")
    public InterviewQuestions getInterViewQuestions(@RequestBody InterviewQuestionInput input){
        return iPrepAIService.getInterviewQuestions(input.getCourseName(),input.getTopicName());
    }

}
