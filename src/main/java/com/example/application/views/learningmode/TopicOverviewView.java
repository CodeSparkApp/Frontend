package com.example.application.views.learningmode;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;

import java.util.Arrays;
import java.util.List;

@PageTitle("Learning Mode - Topic Overview")
@Route("learning-mode/topic-overview")
public class TopicOverviewView extends VerticalLayout {
private int overAllProgress;
    public TopicOverviewView() {

        List<String> topics = Arrays.asList(
                "Die Programmiersprache Java", "Entwicklungsumgebungen", "Java Packages",
                "Primitive Datentypen", "Operatoren", "Kontrollstrukturen", "Arrays",
                "Objektorientierung", "Klassen und Objekte", "Strings und Wrapperklassen",
                "Methoden", "Dokumentation", "Vererbung", "Exceptions", "Input / Output",
                "Grafische Benutzeroberflächen mit Swing","Abstrakte Klassen, Interfaces",
                "Innere Klassen", "Events", "Java FX", "Zeichnen", "Datenstrukturen",
                "Aufzählungstypen", "Threads", "Java 8","XML", "JSON", "JUnit-Tests", "Maven"
        );

        Div topicsContainer = new Div();
        topicsContainer.getStyle()
                .set("display", "flex")
                .set("flex-wrap", "wrap")
                .set("gap", "16px");

        for (String topic : topics) {
            topicsContainer.add(createTopicCard(topic));
        }
        ProgressBar overallprogressBar = new ProgressBar(0, 100, getOverallProgress());
        Button previousButton = new Button("Back", e -> navigatePreviousPage());
        add(previousButton, topicsContainer, overallprogressBar);




    }

    private Div createTopicCard(String topicName) {
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
        card.addClickListener(event -> openTopicDialog(topicName));
        return card;
    }

    private void openTopicDialog(String topicName) {
        Dialog dialog = new Dialog();
        dialog.add(new H2(topicName), new Paragraph("Detailed content for " + topicName));
        dialog.setModal(true);
        dialog.open();
    }
    private int getOverallProgress() {
        return overAllProgress/100;
    }
    private void navigatePreviousPage() {
        getUI().ifPresent(ui -> ui.navigate("/learning-mode"));
    }
}