package com.example.application.views.storymode;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;

@PageTitle("Chapter View")
@Route("story-mode/:id/:chapterName/chapter-content")
public class ChapterView extends VerticalLayout implements BeforeEnterObserver {

    private String id;
    private String chapterName;
    private int chapterNumber;

    public ChapterView() {
        setSpacing(true);
        setPadding(true);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        RouteParameters parameters = event.getRouteParameters();
        this.id = parameters.get("id").orElse("N/A");
        this.chapterName = parameters.get("chapterName").orElse("N/A");

        try {
            this.chapterNumber = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            this.chapterNumber = 1; // Standardwert, falls ID ungültig ist
        }

        removeAll();
        H2 title = new H2("Chapter: " + chapterName);
        Paragraph content = new Paragraph("This is the content of chapter " + chapterNumber);

        Button prevButton = new Button("Previous", e -> navigateToChapter(chapterNumber - 1));
        Button nextButton = new Button("Next", e -> navigateToChapter(chapterNumber + 1));
        Button backButton = new Button("Back to Overview", e -> navigateToOverview());

        prevButton.setEnabled(chapterNumber > 1);

        add(title, content, prevButton, nextButton, backButton);
    }

    private void navigateToChapter(int chapter) {
        getUI().ifPresent(ui -> ui.navigate("story-mode/" + chapter + "/chapter" + chapter + "/chapter-content"));
    }

    private void navigateToOverview() {
        getUI().ifPresent(ui -> ui.navigate("/story-mode"));
    }
}
