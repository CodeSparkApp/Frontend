package com.example.application.data;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;
    private String avatar; // Optional, als URL zu einem Bild gespeichert.

    @Lob
    private String storyProgress; // JSON oder String für den Story Mode Fortschritt.

    @Lob
    private String learningProgress; // JSON oder String für den Learning Mode Fortschritt.

    @ElementCollection
    private Set<String> badges = new HashSet<>(); // Set von Badges.

    private LocalDate examDate; // Datum der Prüfung.

    // Konstruktor mit Parametern (für die Datenbank oder manuelle Initialisierung)
    public User(String username, String email, String avatar, String storyProgress, String learningProgress, Set<String> badges, LocalDate examDate) {
        this.username = username;
        this.email = email;
        this.avatar = avatar;
        this.storyProgress = storyProgress;
        this.learningProgress = learningProgress;
        this.badges = badges != null ? badges : new HashSet<>(); // Vermeidung von NullPointerException
        this.examDate = examDate;
    }

    // Standardkonstruktor (für JPA)
    public User() {
    }

    // Beispielmethode zum Abrufen eines Users (basierend auf userID)
    public static User getUserByUserID(Long userID) {
        // Hier könnte eine Datenbankabfrage oder ein mockup-Code stehen
        // Beispielhaft:
        return new User("sampleUser", "sample@example.com", "avatarUrl", "storyProgressData", "learningProgressData", new HashSet<>(), LocalDate.now());
    }

    // Getter und Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getStoryProgress() {
        return storyProgress;
    }

    public void setStoryProgress(String storyProgress) {
        this.storyProgress = storyProgress;
    }

    public String getLearningProgress() {
        return learningProgress;
    }

    public void setLearningProgress(String learningProgress) {
        this.learningProgress = learningProgress;
    }

    public Set<String> getBadges() {
        return badges;
    }

    public void setBadges(Set<String> badges) {
        this.badges = badges;
    }

    public LocalDate getExamDate() {
        return examDate;
    }

    public void setExamDate(LocalDate examDate) {
        this.examDate = examDate;
    }
}
