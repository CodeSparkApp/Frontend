package com.example.application.components;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.server.StreamResource;
import com.example.application.data.user;

public class UserProfileDialog extends Dialog {
    public UserProfileDialog(int userID,String userName, String avatarUrl, String displayName, String badges) {
        user User = new user();
        //User = user.getUserByUserID(userID);
        // setHeaderTitle(user.getUserByUserID(userID).getUserName());
        setHeaderTitle(userName);

        // Avatar-Bild
        Image avatar = new Image(avatarUrl, "Avatar");
        avatar.setWidth("100px");
        avatar.setHeight("100px");

        // Anzeigename
        TextField displayNameField = new TextField("Anzeigename");
        displayNameField.setValue(displayName);
        displayNameField.setReadOnly(true);

        // Badges
        TextArea badgesField = new TextArea("Badges");
        badgesField.setValue(badges);
        badgesField.setReadOnly(true);

        // Buttons für Spielstand
        Button downloadButton = new Button("Spielstand herunterladen", VaadinIcon.DOWNLOAD.create());
        Button uploadButton = new Button("Spielstand hochladen", VaadinIcon.UPLOAD.create());

        HorizontalLayout buttonLayout = new HorizontalLayout(downloadButton, uploadButton);
        VerticalLayout layout = new VerticalLayout(avatar, displayNameField, badgesField, buttonLayout);

        add(layout);
    }
}
