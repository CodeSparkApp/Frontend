package com.example.application.services;

import java.util.List;

public class QuizService {
    // Dummy implementation of questions
    private List<String> questions = List.of(
            "What is the output of 1 + 1?",
            "What is the capital of France?"
            // Add more questions here
    );

    public List<String> getQuestions() {
        return questions;
    }

    public String getQuestion(int index) {
        return questions.get(index);
    }
}