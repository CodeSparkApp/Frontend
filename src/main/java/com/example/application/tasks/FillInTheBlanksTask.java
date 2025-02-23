package com.example.application.tasks;

import java.util.List;

public class FillInTheBlanksTask extends Task {
    private List<String> codeSnippets; // Code snippets with blanks
    private List<String> correctAnswers; // Correct answers for the blanks

    public FillInTheBlanksTask(String id, String description, int difficulty, List<String> codeSnippets, List<String> correctAnswers) {
        super(id, description, difficulty, "Fill in the Blanks");
        this.codeSnippets = codeSnippets;
        this.correctAnswers = correctAnswers;
    }

    //@Override
    //public boolean solve(List<String> studentAnswers) {
        // Logic to check if the student's answers match the correct answers
    //    return studentAnswers.equals(correctAnswers);
    //}

    public List<String> getCodeSnippets() {
        return codeSnippets;
    }

    public List<String> getCorrectAnswers() {
        return correctAnswers;
    }

    @Override
    public boolean solve() {
        return false;
    }
}
