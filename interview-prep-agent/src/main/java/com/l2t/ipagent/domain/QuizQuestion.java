package com.l2t.ipagent.domain;

import lombok.Data;

import java.util.List;

@Data
public class QuizQuestion {

    private String question;
    private List<String> options;
    private String correctOption;
}
