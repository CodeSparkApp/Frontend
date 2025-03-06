package com.example.application.tasks;

public abstract class Task {
    private int id; // Eindeutige Identifikation
    private String description; // Beschreibung der Aufgabe
    private String category; // Kategorie (z. B. "Programmieraufgabe", "Debugging", etc.)

    public Task(int id, String description, String category) {
        this.id = id;
        this.description = description;
        this.category = category;
    }

    // Getter und Setter
    public int getId() { return id; }
    public String getDescription() { return description; }
    public String getCategory() { return category; }

    // Abstrakte Methode zur Lösung der Aufgabe
    public abstract boolean solve();


}
