package com.example.application.views.storymode;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;

import java.util.HashMap;
import java.util.Map;

@Route("story-mode")
@PageTitle("Story Mode")
@Menu(order = 2, icon = "line-awesome/svg/book-solid.svg")
public class StoryModeView extends VerticalLayout {
    private int overAllProgress;
    private final Map<Integer, String> topics = new HashMap<>();

    public StoryModeView() {
        setSpacing(true);
        setPadding(true);

        // Themen mit IDs versehen
        String[] topicNames = {
                "Die Programmiersprache Java", "Entwicklungsumgebungen", "Java Packages",
                "Primitive Datentypen", "Operatoren", "Kontrollstrukturen", "Arrays",
                "Objektorientierung", "Klassen und Objekte", "Strings und Wrapperklassen",
                "Methoden", "Dokumentation", "Vererbung", "Exceptions", "Input / Output",
                "Überblick Sommersemester", "Grafische Benutzeroberflächen mit Swing",
                "Abstrakte Klassen, Interfaces", "Innere Klassen", "Events", "Java FX",
                "Zeichnen", "Datenstrukturen", "Aufzählungstypen", "Threads", "Java 8",
                "XML", "JSON", "JUnit-Tests", "Maven"
        };

        for (int i = 0; i < topicNames.length; i++) {
            topics.put(i + 1, topicNames[i]);
        }

        Div topicsContainer = new Div();
        topicsContainer.getStyle()
                .set("display", "flex")
                .set("flex-wrap", "wrap")
                .set("gap", "16px");

        for (Map.Entry<Integer, String> entry : topics.entrySet()) {
            topicsContainer.add(createTopicCard(entry.getKey(), entry.getValue()));
        }

        ProgressBar overallProgressBar = new ProgressBar(0, 100, getOverallProgress());
        add(topicsContainer, overallProgressBar);
    }

    private Div createTopicCard(int id, String topicName) {
        Div card = new Div();
        card.getStyle()
                .set("border", "1px solid #ccc")
                .set("border-radius", "8px")
                .set("padding", "16px")
                .set("width", "250px")
                .set("display", "flex")
                .set("flex-direction", "column")
                .set("align-items", "center");

        H2 title = new H2(topicName);
        title.addClassName(LumoUtility.FontSize.MEDIUM);

        ProgressBar progressBar = new ProgressBar(0, 100, Math.random() * 100);
        progressBar.setWidth("100%");

        overAllProgress += progressBar.getValue();

        card.add(title, progressBar);
        card.addClickListener(event -> openChapter(id, topicName));
        return card;
    }

    private int getOverallProgress() {
        return overAllProgress / 100;
    }

    private void openChapter(int topicId, String chapterTitle) {
        getUI().ifPresent(ui -> ui.navigate("chapter/" + topicId + "/" + chapterTitle.replace(" ", "-")));
    }
}
