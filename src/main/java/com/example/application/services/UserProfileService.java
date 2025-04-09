package com.example.application.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.server.VaadinSession;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserProfileService {

    private static final Logger LOGGER = Logger.getLogger(UserProfileService.class.getName());

    private String id;
    private String username;
    private String email;
    private String profileImageUrl;

    /**
     * Holt das Benutzerprofil von der API und speichert die Werte in den Attributen.
     * @return true, wenn der Abruf erfolgreich war, false bei Fehlern.
     */
    public boolean fetchUserProfile() {
        String apiUrl = "https://codespark-api.up.railway.app/api/v1/account/profile";
        VaadinSession session = VaadinSession.getCurrent();

        if (session == null) {
            LOGGER.severe("Keine aktive Vaadin-Session gefunden.");
            return false;
        }

        String accessToken = (String) session.getAttribute("access_token");

        if (accessToken == null || accessToken.isEmpty()) {
            LOGGER.log(Level.WARNING, "Kein Access Token in der Session gefunden.");
            return false;
        }

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Bearer " + accessToken);
            connection.setConnectTimeout(5000);  // 5 Sekunden Timeout
            connection.setReadTimeout(5000);

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                return parseUserProfile(response.toString());
            } else {
                LOGGER.severe("Fehler beim Abrufen des Benutzerprofils. HTTP-Code: " + responseCode);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Fehler beim Abrufen des Benutzerprofils", e);
        }
        return false;
    }

    /**
     * Parst die JSON-Antwort und speichert die Werte in den Instanzvariablen.
     * @param jsonResponse JSON-String der API-Antwort
     * @return true, wenn das Parsen erfolgreich war, sonst false
     */
    private boolean parseUserProfile(String jsonResponse) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonResponse);

            this.id = rootNode.get("id").asText();
            this.username = rootNode.get("username").asText();
            this.email = rootNode.get("email").asText();
            this.profileImageUrl = rootNode.has("profile_image_url") && !rootNode.get("profile_image_url").isNull()
                    ? rootNode.get("profile_image_url").asText()
                    : null;

            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Fehler beim Parsen des JSON-Antwort", e);
        }
        return false;
    }

    // Getter-Methoden für den Zugriff auf die Profildaten
    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }
}
