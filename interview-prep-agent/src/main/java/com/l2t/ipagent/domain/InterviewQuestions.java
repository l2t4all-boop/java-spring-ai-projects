package com.l2t.ipagent.domain;

import lombok.Data;

import java.util.List;

@Data
public class InterviewQuestions {
    private List<Question> questions;
    private List<QuizQuestion> quizQuestions;
}
