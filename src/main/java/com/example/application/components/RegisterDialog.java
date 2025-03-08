package com.example.application.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.server.VaadinSession;
import elemental.json.JsonObject;
import com.vaadin.flow.component.textfield.EmailField;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class RegisterDialog extends Dialog {

    public RegisterDialog() {
        setHeaderTitle("Register");

        TextField usernameField = new TextField("Username");
        EmailField emailField = new EmailField("Email");
        TextField passwordField = new TextField("Password"); // Kein PasswordField wie gewünscht

        Button registerButton = new Button("Register", event -> registerUser(usernameField.getValue(), emailField.getValue(), passwordField.getValue()));

        VerticalLayout layout = new VerticalLayout(usernameField, emailField, passwordField, registerButton);
        layout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        add(layout);
    }

    private void registerUser(String username, String email, String password) {
        try {
            URL url = new URL("https://codespark.up.railway.app/api/v1/account/register");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

            JsonObject jsonRequest = elemental.json.Json.createObject();
            jsonRequest.put("username", username);
            jsonRequest.put("email", email);
            jsonRequest.put("password", password);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonRequest.toJson().getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (Scanner scanner = new Scanner(connection.getInputStream(), StandardCharsets.UTF_8.name())) {
                    String responseBody = scanner.useDelimiter("\\A").next();
                    Notification successNotification = Notification.show("Registration successful: " + responseBody);
                    successNotification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                }
            } else {
                Notification errorNotification = Notification.show("Registration failed: HTTP " + responseCode);
                errorNotification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        } catch (Exception e) {
            Notification errorNotification = Notification.show("Registration failed: " + e.getMessage());
            errorNotification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }
}
