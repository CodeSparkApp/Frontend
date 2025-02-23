package com.example.application.tasks;

public abstract class Task {
    private String id; // Eindeutige Identifikation
    private String description; // Beschreibung der Aufgabe
    private int difficulty; // Schwierigkeitsgrad der Aufgabe
    private String category; // Kategorie (z. B. "Programmieraufgabe", "Debugging", etc.)

    public Task(String id, String description, int difficulty, String category) {
        this.id = id;
        this.description = description;
        this.difficulty = difficulty;
        this.category = category;
    }

    // Getter und Setter
    public String getId() { return id; }
    public String getDescription() { return description; }
    public int getDifficulty() { return difficulty; }
    public String getCategory() { return category; }

    // Abstrakte Methode zur Lösung der Aufgabe
    public abstract boolean solve();


}
