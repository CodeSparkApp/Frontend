package com.example.application.data;

import com.vaadin.flow.component.template.Id;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class User {
    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;
    private String avatar; // Optional, kann als URL zu einem Bild gespeichert werden.
    private LocalDateTime registrationDate;

//    @OneToMany(mappedBy = "user")
//    private List<UserTaskProgress> userTaskProgress;
//    @OneToMany(mappedBy = "user")
//    private List<UserChapterProgress> userChapterProgresses;

    @OneToOne
    private UserExamTimer userExamTimer;

    @ElementCollection
    private Set<String> badges; // Optional: Set von Badges oder Referenz zu einer Badge-Tabelle

    public static User getUserByUserID(int userID) {
        return new User();
    }

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

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }
//
//    public List<UserTaskProgress> getUserTaskProgress() {
//        return userTaskProgress;
//    }
//
//    public void setUserTaskProgress(List<UserTaskProgress> userTaskProgress) {
//        this.userTaskProgress = userTaskProgress;
//    }

    public UserExamTimer getUserExamTimer() {
        return userExamTimer;
    }

    public void setUserExamTimer(UserExamTimer userExamTimer) {
        this.userExamTimer = userExamTimer;
    }

    public Set<String> getBadges() {
        return badges;
    }

    public void setBadges(Set<String> badges) {
        this.badges = badges;
    }

//    public List<UserChapterProgress> getUserChapterProgresses() {
//        return userChapterProgresses;
//    }
//
//    public void setUserChapterProgresses(List<UserChapterProgress> userChapterProgresses) {
//        this.userChapterProgresses = userChapterProgresses;
//    }
    public Integer getStoryPoints() {
        return 1720;
    }

// Getter und Setter
}

