package com.example.application.components;

import com.example.application.data.User;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;

import java.util.Set;
import java.util.stream.Collectors;

public class UserProfileDialog extends Dialog {

    public UserProfileDialog(User currentUser) {
        setHeaderTitle("Benutzerprofil");

        // Avatar
        Image avatar = new Image(currentUser.getAvatar(), "Profilbild");
        avatar.setWidth("100px");
        avatar.setHeight("100px");

        // Benutzername
        Span usernameSpan = new Span(currentUser.getUsername());
        usernameSpan.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.FontWeight.BOLD);

        // Badges anzeigen
        Set<String> badges = currentUser.getBadges();
        String badgeText = (badges != null && !badges.isEmpty())
                ? badges.stream().collect(Collectors.joining(", "))
                : "Keine Badges";
        Span badgesSpan = new Span("Badges: " + badgeText);

        // Story-Progress
        String storyProgress = currentUser.getStoryProgress() != null ? currentUser.getStoryProgress() : "Nicht gestartet";
        Span storyProgressSpan = new Span("Story-Fortschritt: " + storyProgress);

        // Learning-Progress
        String learningProgress = currentUser.getLearningProgress() != null ? currentUser.getLearningProgress() : "Nicht gestartet";
        Span learningProgressSpan = new Span("Lernfortschritt: " + learningProgress);

        // Layout für Benutzerinformationen
        VerticalLayout userInfoLayout = new VerticalLayout(usernameSpan, badgesSpan, storyProgressSpan, learningProgressSpan);
        userInfoLayout.setSpacing(false);

        // Hauptlayout mit Avatar
        HorizontalLayout mainLayout = new HorizontalLayout(avatar, userInfoLayout);
        mainLayout.setSpacing(true);
        mainLayout.setPadding(true);

        // Schließen-Button
        Button closeButton = new Button("Schließen", event -> close());

        // Dialog-Inhalt hinzufügen
        add(mainLayout);
        getFooter().add(closeButton);
    }
}
