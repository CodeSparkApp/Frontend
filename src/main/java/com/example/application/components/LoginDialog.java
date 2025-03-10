package com.example.application.components;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.server.VaadinSession;
import elemental.json.JsonObject;
import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class LoginDialog extends Dialog {

    public LoginDialog() {
        setHeaderTitle("Login");

        TextField usernameField = new TextField("Username or Email");
        TextField passwordField = new TextField("Password");

       Button loginButton = new Button("Login", event -> loginUser(usernameField.getValue(), passwordField.getValue()));
        //Button loginButton = new Button("Login", event -> loginUser("josha1", "12345"));

        VerticalLayout layout = new VerticalLayout(usernameField, passwordField, loginButton);
        layout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        add(layout);
    }

    private void loginUser(String usernameOrEmail, String password) {
        try {
            URL url = new URL("https://codespark.up.railway.app/api/v1/auth/login");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

            // JSON-Objekt für die Anfrage erstellen
            JsonObject jsonRequest = elemental.json.Json.createObject();
            jsonRequest.put("username_or_email", usernameOrEmail);
            jsonRequest.put("password", password);

            // JSON-Daten an den Server senden
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonRequest.toJson().getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            System.out.println("HTTP Response Code: " + responseCode);

            // Wenn die Anmeldung erfolgreich war
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (Scanner scanner = new Scanner(connection.getInputStream(), StandardCharsets.UTF_8.name())) {
                    String responseBody = scanner.useDelimiter("\\A").next();
                    System.out.println("Response Body: " + responseBody);

                    // JSON-Antwort parsen und Tokens extrahieren
                    JSONObject jsonResponse = new JSONObject(responseBody);

                    String accessToken = jsonResponse.getString("access_token");
                    String refreshToken = jsonResponse.getString("refresh_token");

                    // Tokens speichern
                    storeTokens(accessToken, refreshToken);

                    //LoggedIn true setzen
                    VaadinSession.getCurrent().setAttribute("logged_in", true);

                    // Erfolgsmeldung anzeigen
                    Notification successNotification = Notification.show("Login successful");
                    successNotification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                    UI.getCurrent().getPage().reload(); // Neuladen der gesamten Seite
                }
            } else {
                try (Scanner scanner = new Scanner(connection.getInputStream(), StandardCharsets.UTF_8.name())) {
                    String errorBody = scanner.useDelimiter("\\A").next();
                    System.out.println("Error Response: " + errorBody);

                    //LoggedIn false setzen
                    VaadinSession.getCurrent().setAttribute("logged_in", false);


                    // Fehleranzeige bei fehlerhaftem Login
                    Notification errorNotification = Notification.show("Login failed: " + errorBody);
                    errorNotification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                }
            }
        } catch (Exception e) {
            // Fehlerbehandlung bei Exceptions
            Notification errorNotification = Notification.show("Login failed: " + e.getMessage());
            errorNotification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            System.out.println("Exception: " + e.getMessage());
        }
    }

    private void storeTokens(String accessToken, String refreshToken) {
        // Tokens in der VaadinSession speichern
        VaadinSession.getCurrent().setAttribute("access_token", accessToken);
        VaadinSession.getCurrent().setAttribute("refresh_token", refreshToken);
    }

}
