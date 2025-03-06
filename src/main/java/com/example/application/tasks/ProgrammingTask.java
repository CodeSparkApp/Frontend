package com.example.application.tasks;

public class ProgrammingTask extends Task {
    private String sampleCode; // Beispielcode, der angezeigt werden kann

    public ProgrammingTask(int id, String description, String sampleCode) {
        super(id, description, "Programming Task");
        this.sampleCode = sampleCode;
    }

    @Override
    public boolean solve() {
        // Logik zur Überprüfung der Lösung
        return false; // Beispielwert
    }
}
