package com.example.application.views.storymode;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@PageTitle("Chapter")
@Route("story-mode/lesson/:id/:lessonName")
public class ChapterBackendView extends VerticalLayout implements BeforeEnterObserver {

    private String lessonId;
    private String lessonName;

    public ChapterBackendView() {
        setSpacing(true);
        setPadding(true);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        lessonId = event.getRouteParameters().get("id").orElse("");
        lessonName = event.getRouteParameters().get("lessonName").orElse("");

        if (!lessonId.isEmpty()) {
            // API-Aufruf zur Lektionenübersicht mit der lessonId
            loadLessonDetails();
        } else {
            Notification.show("Lesson ID is missing!");
        }
    }

    private void loadLessonDetails() {
        // Die API-URL für den GET-Request mit lessonId
        String apiUrl = "https://codespark.up.railway.app/api/v1/lesson/" + lessonId;

        try {
            VaadinSession session = VaadinSession.getCurrent();
            String accessToken = (String) session.getAttribute("access_token");

            if (accessToken == null) {
                Notification.show("Fehler: Kein Zugriffstoken vorhanden!");
                return;
            }

            // Die HTTP-Verbindung herstellen
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Bearer " + accessToken);
            connection.setRequestProperty("Accept", "application/json");

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                // Antwort verarbeiten
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonResponse = objectMapper.readTree(reader);
                reader.close();

                // Lektion Details extrahieren
                String title = jsonResponse.get("title").asText();
                String description = jsonResponse.get("description").asText();
                String type = jsonResponse.get("type").asText();

                // Rendern der Lektion Details
                renderLessonDetails(title, description, type);
            } else {
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                StringBuilder errorMessage = new StringBuilder();
                String line;
                while ((line = errorReader.readLine()) != null) {
                    errorMessage.append(line);
                }
                errorReader.close();
                Notification.show("Fehler beim Laden der Lektion: " + responseCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
            Notification.show("Ein Fehler ist aufgetreten: " + e.getMessage());
        }
    }

    private void renderLessonDetails(String title, String description, String type) {
        // Titel der Lektion
        H2 lessonTitle = new H2(title);
        lessonTitle.getStyle().set("margin-top", "20px");

        // Beschreibung der Lektion
        Paragraph lessonDescription = new Paragraph(description);

        // Typ der Lektion
        Paragraph lessonType = new Paragraph("Type: " + type);

        // Komponenten zum Layout hinzufügen
        add(lessonTitle, lessonDescription, lessonType);

        // Wenn die Lektion eine Programmieraufgabe ist, den Code-Editor anzeigen
        if ("PROGRAMMING".equals(type)) {
            addProgrammingTask();
        }
    }

    private void addProgrammingTask() {
        // Anweisungen für die Aufgabe
        Paragraph instruction = new Paragraph("Write a complete Java program that fulfills the following requirement:");

        // Beispiel für die Problemstellung
        Paragraph problemStatement = new Paragraph(
                "Write a Java program that takes an integer as input and prints whether it is even or odd.");

        // Textbereich für den Code
        TextArea codeEditor = new TextArea("Your Java Code");
        codeEditor.setPlaceholder("Write your Java code here...");
        codeEditor.setWidthFull();
        codeEditor.setHeight("300px");

        // Absenden-Button
        Button submitButton = new Button("Submit", event -> {
            String studentCode = codeEditor.getValue();
            // Hier kannst du die Logik zum Validieren oder Ausführen des Java-Codes hinzufügen
            System.out.println("Submitted Code:\n" + studentCode);
        });

        // Code-Editor und Button zum Layout hinzufügen
        add(instruction, problemStatement, codeEditor, submitButton);
    }
}
