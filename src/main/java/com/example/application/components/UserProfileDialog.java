package com.example.application.components;

import com.example.application.data.User;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;

public class UserProfileDialog extends Dialog {

    public UserProfileDialog(Long userId, String username, String avatarUrl, String badges, String storyProgress, String learningProgress) {
        setHeaderTitle("Benutzerprofil");

        // Avatar
        Image avatar = new Image(avatarUrl, "Profilbild");
        avatar.setWidth("100px");
        avatar.setHeight("100px");

        // Benutzername
        Span usernameSpan = new Span(username);
        usernameSpan.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.FontWeight.BOLD);

        // Badges
        Span badgesSpan = new Span("Badges: " + (badges != null ? badges : "Keine Badges"));

        // Story-Progress
        Span storyProgressSpan = new Span("Story-Fortschritt: " + (storyProgress != null ? storyProgress : "Nicht gestartet"));

        // Learning-Progress
        Span learningProgressSpan = new Span("Lernfortschritt: " + (learningProgress != null ? learningProgress : "Nicht gestartet"));

        // Layout für Benutzerinformationen
        VerticalLayout userInfoLayout = new VerticalLayout(usernameSpan, badgesSpan, storyProgressSpan, learningProgressSpan);
        userInfoLayout.setSpacing(false);

        // Hauptlayout
        HorizontalLayout mainLayout = new HorizontalLayout(avatar, userInfoLayout);
        mainLayout.setSpacing(true);

        // Schließen-Button
        Button closeButton = new Button("Schließen", event -> close());

        add(mainLayout, closeButton);
    }

    public UserProfileDialog(User currentUser) {
    }
}
