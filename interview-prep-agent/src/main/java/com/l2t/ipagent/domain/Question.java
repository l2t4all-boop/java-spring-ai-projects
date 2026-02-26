package com.l2t.ipagent.domain;

import lombok.Data;

@Data
public class Question {
    private String question;
    private String answer;
    private String example;
    private String exampleWithCode;
}
