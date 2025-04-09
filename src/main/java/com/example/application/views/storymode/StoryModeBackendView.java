package com.example.application.views.storymode;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.lumo.LumoUtility;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Route("story-mode-backend")
@PageTitle("Story Mode Backend")
@Menu(order = 2, icon = "line-awesome/svg/book-solid.svg")
public class StoryModeBackendView extends VerticalLayout {
    private int overAllProgress;

    public StoryModeBackendView() {
        setSpacing(true);
        setPadding(true);

        // API-Aufruf zur Kapitelübersicht
        List<Chapter> chapters = fetchChapters();

        Div topicsContainer = new Div();
        topicsContainer.getStyle()
                .set("display", "flex")
                .set("flex-wrap", "wrap")
                .set("gap", "16px");

        for (Chapter chapter : chapters) {
            topicsContainer.add(createTopicCard(chapter));
        }

        ProgressBar overallProgressBar = new ProgressBar(0, 100, getOverallProgress());
        add(topicsContainer, overallProgressBar);
    }

    private List<Chapter> fetchChapters() {
        List<Chapter> chapters = new ArrayList<>();
        String apiUrl = "https://codespark-api.up.railway.app/api/v1/chapter/overview";

        try {

            String accessToken = (String) VaadinSession.getCurrent().getAttribute("access_token");
            if (accessToken == null) {
                Notification.show("Fehler: Kein Zugriffstoken vorhanden!");
                return chapters;
            }

            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Bearer " + accessToken);
            connection.setRequestProperty("Accept", "application/json");

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) { // Nur wenn der Status-Code 200 ist
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(reader);
                reader.close();

                for (JsonNode chapterNode : jsonNode.get("chapters")) {
                    String id = chapterNode.get("id").asText();
                    String title = chapterNode.get("title").asText();
                    String progress = chapterNode.get("progress").asText();
                    chapters.add(new Chapter(id, title, progress));
                }
            } else { // Fehlerbehandlung bei nicht erfolgreichem Status-Code
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                StringBuilder errorMessage = new StringBuilder();
                String line;
                while ((line = errorReader.readLine()) != null) {
                    errorMessage.append(line);
                }
                errorReader.close();

                System.err.println("HTTP-Fehler: " + responseCode);
                System.err.println("Fehlermeldung: " + errorMessage);
                Notification.show("Fehler beim Laden der Kapitel: " + responseCode);
            }
        } catch (Exception e) {
            System.err.println("Exception beim Laden der Kapitel: " + e.getMessage());
            Notification.show("Ein Fehler ist aufgetreten: " + e.getMessage());
        }

        return chapters;
    }

    private Div createTopicCard(Chapter chapter) {
        Div card = new Div();
        card.getStyle()
                .set("border", "1px solid #ccc")
                .set("border-radius", "8px")
                .set("padding", "16px")
                .set("width", "250px")
                .set("display", "flex")
                .set("flex-direction", "column")
                .set("align-items", "center");
                //.set("box-shadow", "inset 4px 0 0 hsl(0, 80%, 50%), inset -4px 0 0 hsl(120, 60%, 40%)");

        H2 title = new H2(chapter.getTitle());
        title.addClassName(LumoUtility.FontSize.MEDIUM);
        double progress = 0.0;

        try {
            double doubleValue = Double.parseDouble(chapter.getProgress());
            progress = doubleValue;  // Ausgabe: 123.45
        } catch (NumberFormatException e) {
            System.out.println("Ungültiger Progress!");
        }
        ProgressBar progressBar = new ProgressBar(0.0, 1.0, progress);
        progressBar.setWidth("100%");

        overAllProgress += progressBar.getValue();

        card.add(title, progressBar);
        card.addClickListener(event -> openChapter(chapter.getId(), chapter.getTitle()));
        return card;
    }

    private int getOverallProgress() {
        return overAllProgress / 100;
    }

    private void openChapter(String chapterId, String chapterTitle) {
        getUI().ifPresent(ui -> ui.navigate("story-mode/chapter/" + chapterId ));
    }

    private static class Chapter {
        private final String id;
        private final String title;
        private final String progress;

        public Chapter(String id, String title, String progress) {
            this.id = id;
            this.title = title;
            this.progress = progress;
        }

        public String getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }
        public String getProgress() {return progress;}
    }
}
