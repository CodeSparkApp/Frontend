package com.example.application.tasks;

import java.util.List;

public class MultipleChoiceTask extends Task {
    private List<String> options; // Liste der Antwortoptionen
    private String correctSolution; // Die richtige Antwort

    public MultipleChoiceTask(int id, String description, List<String> options, String correctSolution) {
        super(id, description, "Multiple-Choice");
        this.options = options;
        this.correctSolution = correctSolution;
    }

    @Override
    public boolean solve() {
        // Logik zur Überprüfung der Lösung
        return false; // Beispielwert
    }

    // Getter für Optionen
    public List<String> getOptions() {
        return options;
    }
}
