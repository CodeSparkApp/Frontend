package com.example.application.services;

import com.example.application.views.storymode.StoryModeBackendView;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.server.VaadinSession;
import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Scanner;

public class TokenManager {
    private static final String REFRESH_URL = "https://codespark.up.railway.app/api/v1/auth/refresh";

    public static String getAccessToken() {
        String accessToken = (String) VaadinSession.getCurrent().getAttribute("access_token");

        // Prüfen, ob der Token gültig ist
        if (accessToken == null || isTokenExpired(accessToken)) {
            return refreshAccessToken();
        }

        return accessToken;
    }

    public static boolean isUserLoggedIn() {
        String accessToken = (String) VaadinSession.getCurrent().getAttribute("access_token");

        // Prüfen, ob Token existiert und gültig ist
        if (accessToken == null || isTokenExpired(accessToken)) {
            return refreshAccessToken() != null;
        }

        return true;
    }

    public static void logoutUser() {
        VaadinSession.getCurrent().setAttribute("logged_in", false);
        VaadinSession.getCurrent().setAttribute("access_token", null);
        VaadinSession.getCurrent().setAttribute("refresh_token", null);
    }

    private static String refreshAccessToken() {
        String refreshToken = (String) VaadinSession.getCurrent().getAttribute("refresh_token");

        if (refreshToken == null) {
            logoutUser();
            return null;
        }

        try {
            URL url = new URL(REFRESH_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

            JSONObject jsonRequest = new JSONObject();
            jsonRequest.put("refresh_token", refreshToken);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonRequest.toString().getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (Scanner scanner = new Scanner(connection.getInputStream(), StandardCharsets.UTF_8.name())) {
                    String responseBody = scanner.useDelimiter("\\A").next();
                    JSONObject jsonResponse = new JSONObject(responseBody);
                    String newAccessToken = jsonResponse.getString("access_token");

                    // Speichere den neuen Token
                    VaadinSession.getCurrent().setAttribute("access_token", newAccessToken);

                    return newAccessToken;
                }
            }
            else {
                logoutUser();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        logoutUser();
        return null;
    }

    private static boolean isTokenExpired(String token) {

        String apiUrl = "https://codespark.up.railway.app/api/v1/chapter/overview";

        try {

            String accessToken = (String) VaadinSession.getCurrent().getAttribute("access_token");
            if (accessToken == null) {
                Notification.show("Fehler: Kein Zugriffstoken vorhanden!");
            }

            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Bearer " + accessToken);
            connection.setRequestProperty("Accept", "application/json");

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                return false;// Nur wenn der Status-Code 200 ist
            }
            else {
                return true;
            }

        }
        catch(Exception e){
            System.err.println("Exception beim Laden der Kapitel: " + e.getMessage());
            Notification.show("Ein Fehler ist aufgetreten: " + e.getMessage());
        }
        return true;
    }

}
