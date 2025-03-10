package com.example.application.views.storymode;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Route("story-mode/chapter/:id")
public class ChapterOverviewBackendView extends VerticalLayout implements BeforeEnterObserver {
    private String chapterId;
    private String chapterTitle;
    private List<Lesson> lessons;

    public ChapterOverviewBackendView() {
        setSpacing(true);
        setPadding(true);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        chapterId = event.getRouteParameters().get("id").orElse("");
        //chapterTitle = event.getRouteParameters().get("title").orElse("");

        if (!chapterId.isEmpty()) {
            // API-Aufruf zur Lektionenübersicht
            lessons = fetchLessonsForChapter(chapterId);
            if (lessons != null) {
                renderChapterOverview();
            }
        }
    }

    private List<Lesson> fetchLessonsForChapter(String chapterId) {
        List<Lesson> lessons = new ArrayList<>();
        String apiUrl = "https://codespark.up.railway.app/api/v1/chapter/" + chapterId + "/lessons";

        try {
            VaadinSession session = VaadinSession.getCurrent();
            String accessToken = (String) session.getAttribute("access_token");

            if (accessToken == null) {
                Notification.show("Fehler: Kein Zugriffstoken vorhanden!");
                return lessons;
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

                // Kapitelübersicht laden
                for (JsonNode lessonNode : jsonNode.get("lessons")) {
                    String id = lessonNode.get("id").asText();
                    String title = lessonNode.get("title").asText();
                    chapterTitle = title;
                    lessons.add(new Lesson(id, title));
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
                Notification.show("Fehler beim Laden der Lektionen: " + responseCode);
            }
        } catch (Exception e) {
            System.err.println("Exception beim Laden der Lektionen: " + e.getMessage());
            Notification.show("Ein Fehler ist aufgetreten: " + e.getMessage());
        }

        return lessons;
    }

    private void renderChapterOverview() {
        Div chapterTitleDiv = new Div();
        chapterTitleDiv.setText("Kapitel: " + chapterTitle);
        add(chapterTitleDiv);

        for (Lesson lesson : lessons) {
            add(createLessonCard(lesson));
        }
    }
    private Div createLessonCard(Lesson lesson) {
        Div card = new Div();
        card.getStyle()
                .set("border", "1px solid #ccc")
                .set("border-radius", "8px")
                .set("padding", "16px")
                .set("width", "250px")
                .set("display", "flex")
                .set("flex-direction", "column")
                .set("align-items", "center");

        H2 title = new H2(lesson.getTitle());
        title.addClassName("lesson-title");

        // Hier wird ein zufälliger Fortschritt verwendet, das könnte mit realen Daten ersetzt werden.
        ProgressBar progressBar = new ProgressBar(0, 100, Math.random() * 100);
        progressBar.setWidth("100%");

        card.add(title, progressBar);
        card.addClickListener(event -> openLesson(lesson.getId(), lesson.getTitle()));
        return card;
    }
//    private Div createLessonButton(Lesson lesson) {
//        Div lessonDiv = new Div();
//        lessonDiv.setText(lesson.getTitle());
//        lessonDiv.getStyle()
//                .set("border", "1px solid #ccc")
//                .set("border-radius", "8px")
//                .set("padding", "16px")
//                .set("width", "250px")
//                .set("display", "flex")
//                .set("flex-direction", "column")
//                .set("align-items", "center");
//
//        lessonDiv.addClickListener(event -> openLesson(lesson.getId(), lesson.getTitle()));
//        return lessonDiv;
//    }

    private void openLesson(String lessonId, String lessonTitle) {
        getUI().ifPresent(ui -> ui.navigate("story-mode/lesson/" + lessonId + "/" + lessonTitle.replace(" ", "-")));
    }

    // Klasse für Lektion
    private static class Lesson {
        private final String id;
        private final String title;

        public Lesson(String id, String title) {
            this.id = id;
            this.title = title;
        }

        public String getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }
    }
}
