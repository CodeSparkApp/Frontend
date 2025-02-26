package com.example.application.views.storymode;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.HashMap;
import java.util.Map;

@Route("chapter")
@PageTitle("Chapter Overview")
public class ChapterOverviewView extends VerticalLayout implements HasUrlParameter<String> {
    private static final Map<Integer, String> CHAPTER_DESCRIPTIONS = new HashMap<>();

    static {
        CHAPTER_DESCRIPTIONS.put(1, "This chapter introduces Java programming fundamentals.");
        CHAPTER_DESCRIPTIONS.put(2, "Learn about different Java development environments.");
        CHAPTER_DESCRIPTIONS.put(3, "Explore Java Packages and their usage.");
        // Weitere Kapitelbeschreibungen hier hinzufügen
    }

    public ChapterOverviewView() {
        setSpacing(true);
        setPadding(true);
    }

    @Override
    public void setParameter(BeforeEvent event, String parameter) {
        if (parameter == null || parameter.isEmpty()) {
            showErrorDialog("Invalid Chapter");
            return;
        }

        String[] params = parameter.split("/");
        if (params.length < 2) { // Erwartet wird: id/chapterName/extraParameter
            showErrorDialog("Invalid Chapter Format");
            return;
        }

        try {
            int chapterId = Integer.parseInt(params[0]);
            String chapterName = params[1].replace("-", " ");
            String extraParameter = params[2];

            // Setzt den Seitentitel
            getUI().ifPresent(ui -> ui.getPage().setTitle(chapterName));

            // Kapitelbeschreibung abrufen oder Standardtext setzen
            String description = CHAPTER_DESCRIPTIONS.getOrDefault(chapterId, "No description available.");

            // UI-Elemente erstellen
            H2 chapterTitle = new H2("Chapter " + chapterId + ": " + chapterName);
            Paragraph chapterDescription = new Paragraph(description);

            Button startButton = new Button("Start Chapter", e -> startChapter(chapterId, chapterName, extraParameter));
            Button backButton = new Button("Back", e -> navigatePreviousPage());

            add(chapterTitle, chapterDescription, backButton, startButton);

        } catch (NumberFormatException e) {
            showErrorDialog("Invalid Chapter ID");
        }
    }

    private void startChapter(int chapterId, String chapterName, String extraParameter) {
        getUI().ifPresent(ui -> ui.navigate("chapter-content/" + chapterId + "/" + chapterName.replace(" ", "-") + "/" + extraParameter));
    }

    private void navigatePreviousPage() {
        getUI().ifPresent(ui -> ui.navigate("/story-mode"));
    }

    private void showErrorDialog(String message) {
        Dialog dialog = new Dialog(new Text(message));
        dialog.open();
    }
}