package com.example.application.components;

import com.example.application.data.User;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.lumo.LumoUtility;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UserProfileDialog extends Dialog {

    public UserProfileDialog(User currentUser) {
        setHeaderTitle("Benutzerprofil");

        // Avatar
        Image avatar = new Image(currentUser.getAvatar(), "Profilbild");
        avatar.setWidth("100px");
        avatar.setHeight("100px");
        avatar.getStyle().set("margin", "auto").set("border-radius", "25%");

        Icon editIcon = VaadinIcon.EDIT.create();
        editIcon.addClickListener(event -> openImageUploadDialog());

        // Benutzername
        Span usernameSpan = new Span(currentUser.getUsername());
        usernameSpan.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.FontWeight.BOLD);

        // Badges anzeigen
        Set<String> badges = currentUser.getBadges();
        String badgeText = (badges != null && !badges.isEmpty())
                ? badges.stream().collect(Collectors.joining(", "))
                : "Keine Badges";
        Span badgesSpan = new Span("Badges: " + badgeText);


        // Story-Progress als ProgressBar
        String storyProgress = currentUser.getStoryProgress() != null ? currentUser.getStoryProgress() : "Nicht gestartet";
        ProgressBar storyProgressBar = new ProgressBar();
        storyProgressBar.setValue(0.75);
        //storyProgressBar.setValue(currentUser.getStoryProgressPercent() / 100.0);
        Span storyProgressSpan = new Span("Story-Fortschritt: " + storyProgress);

        // Learning-Progress als ProgressBar
        String learningProgress = currentUser.getLearningProgress() != null ? currentUser.getLearningProgress() : "Nicht gestartet";
        ProgressBar learningProgressBar = new ProgressBar();
        learningProgressBar.setValue(0.25);
        //learningProgressBar.setValue(currentUser.getLearningProgressPercent() / 100.0);
        Span learningProgressSpan = new Span("Lernfortschritt: " + learningProgress);

        HorizontalLayout avatarLayout = new HorizontalLayout(avatar, editIcon);
        avatarLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        avatarLayout.setSpacing(true);

        // Layout für Benutzerinformationen
        List<Component> components = new ArrayList<>();
        components.add(usernameSpan);
        if (false) {
            components.add(badgesSpan);
        }
        components.add(storyProgressSpan);
        components.add(storyProgressBar);
        components.add(learningProgressSpan);
        components.add(learningProgressBar);


        VerticalLayout userInfoLayout = new VerticalLayout(components.get(0), components.get(1), components.get(2), components.get(3), components.get(4));
        userInfoLayout.setSpacing(true);

        // Layout für Avatar und Benutzerinformationen
        VerticalLayout mainLayout = new VerticalLayout(avatarLayout, userInfoLayout);
        mainLayout.setSpacing(true);
        mainLayout.setPadding(true);

        //Button zum ausloggen
        Button logoutButton = new Button("Logout", event -> {
            VaadinSession.getCurrent().setAttribute("logged_in", false);
            VaadinSession.getCurrent().setAttribute("access_token", null);
            VaadinSession.getCurrent().setAttribute("refresh_token", null);

            UI.getCurrent().getPage().reload(); // Neuladen der gesamten Seite
            Notification notification = new Notification("" + VaadinSession.getCurrent().getAttribute("access_token"));
            notification.open();

        });

        // Button für weitere Optionen
        Button closeButton = new Button("Schließen", event -> close());



        // Dialog-Inhalt hinzufügen
        add(mainLayout);
        getFooter().add(logoutButton);
        getFooter().add(closeButton);
    }
    private void openImageUploadDialog() {
        // Öffne den Dialog zum Hochladen des Bildes
        ImageUploadDialog imageUploadDialog = new ImageUploadDialog();
        imageUploadDialog.open();
    }
    // Bild-Upload-Dialog
    public class ImageUploadDialog extends Dialog {
        public ImageUploadDialog() {
            setHeaderTitle("Profilbild ändern");

            // Upload-Button
            MultiFileMemoryBuffer buffer = new MultiFileMemoryBuffer();
            Upload upload = new Upload(buffer);

            upload.addSucceededListener(event -> {
                String fileName = event.getFileName();
                InputStream inputStream = buffer.getInputStream(fileName);

                // Do something with the file data
                // processFile(inputStream, fileName);
            });
            upload.setAcceptedFileTypes("image/png", "image/jpeg");
            upload.setMaxFiles(1);

            // Layout für den Upload
            VerticalLayout uploadLayout = new VerticalLayout(upload);
            uploadLayout.setAlignItems(FlexComponent.Alignment.CENTER);
            add(uploadLayout);

            // Schließen-Button
            Button closeButton = new Button("Schließen", event -> close());

            // Confirm-Button
            Button confirmButton = new Button("Confirm", event -> {
                Notification.show("Avatar uploaded");
            });
            getFooter().add(confirmButton, closeButton);
        }
    }
}
