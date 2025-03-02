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
    private Set<String> badges; // Set von Badges.

    private LocalDate examDate; // Datum der Prüfung.

    public User(int id, String name, String mail, String avatar, String story_progress, String learning_progress, String badges, Object exam_date) {
        this.id = (long) id;
        this.username = name;
        this.email = mail;
        this.avatar = avatar;
        this.storyProgress = story_progress;
        this.learningProgress = learning_progress;
        this.badges = new HashSet<>();

    }

    public User() {

    }

    public static User getUserByUserID(int userID) {
        return new User();
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
