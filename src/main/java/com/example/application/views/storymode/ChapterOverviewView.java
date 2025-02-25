package com.example.application.views.storymode;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Chapter Overview")
@Route("chapter/:chapterName")
public class ChapterOverviewView extends VerticalLayout {
    public ChapterOverviewView() {
        setSpacing(true);
        setPadding(true);

        H2 chapterTitle = new H2("Chapter Overview");
        add(chapterTitle);

        Paragraph chapterDescription = new Paragraph("This chapter covers fundamental concepts in Java.");
        add(chapterDescription);

        Button startButton = new Button("Start Chapter", e -> startChapter());
        add(startButton);
    }

    private void startChapter() {
        getUI().ifPresent(ui -> ui.navigate("chapter-content"));
    }
}
