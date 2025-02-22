package com.example.application.data;

import jakarta.annotation.Nonnull;

import java.util.Date;

public class user {
    @Nonnull
    private String userID;

    @Nonnull
    private String userName;

    @Nonnull
    private int storyPoints;

    @Nonnull
    private int learningPoints;

    @Nonnull
    private Date birthday;

    @Nonnull
    public String getUserName() {
        return userName;
    }

    public void setUserName(@Nonnull String userName) {
        this.userName = userName;
    }

    public int getStoryPoints() {
        return storyPoints;
    }

    public void setStoryPoints(int storyPoints) {
        this.storyPoints = storyPoints;
    }

    @Nonnull
    public String getUserID() {
        return userID;
    }

    public void setUserID(@Nonnull String userID) {
        this.userID = userID;
    }

    public int getLearningPoints() {
        return learningPoints;
    }

    public void setLearningPoints(int learningPoints) {
        this.learningPoints = learningPoints;
    }

    @Nonnull
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(@Nonnull Date birthday) {
        this.birthday = birthday;
    }
    @Nonnull
    public user getUserByUserID(int userID) {
        return this;
    }
}
