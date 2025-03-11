package com.example.application.views.storymode;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;

@PageTitle("Chapter Overview")
@Route("story-mode/:id/:chapterName") // Route mit Platzhaltern für Parameter
public class ChapterOverviewView extends VerticalLayout implements BeforeEnterObserver {

    private String id;
    private String chapterName;
    private TextField idField;
    private TextField chapterNameField;

    public ChapterOverviewView() {
        setSpacing(true);
        setPadding(true);

        H2 chapterTitle = new H2("Chapter Overview");
        add(chapterTitle);

        Paragraph chapterDescription = new Paragraph("This chapter covers fundamental concepts in Java.");
        add(chapterDescription);

        // Textfelder für die Anzeige der Parameter
        idField = new TextField("ID");
        idField.setReadOnly(true); // Nur zur Anzeige, nicht bearbeitbar
        add(idField);

        chapterNameField = new TextField("Chapter Name");
        chapterNameField.setReadOnly(true); // Nur zur Anzeige, nicht bearbeitbar
        add(chapterNameField);

        Button startButton = new Button("Start Chapter", e -> startChapter());
        Button previousButton = new Button("Back", e -> navigatePreviousPage());
        add(previousButton, startButton);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        // Extrahiere die Parameter aus der URL
        RouteParameters parameters = event.getRouteParameters();
        this.id = parameters.get("id").orElse("N/A"); // Standardwert, falls nicht vorhanden
        this.chapterName = parameters.get("chapterName").orElse("N/A");

        // Setze die Werte in den Textfeldern
        idField.setValue(this.id);
        chapterNameField.setValue(this.chapterName);
    }

    private void startChapter() {
        getUI().ifPresent(ui -> ui.navigate("story-mode/" + this.id + "/" + chapterName + "/chapter-content"));
    }

    private void navigatePreviousPage() {
        getUI().ifPresent(ui -> ui.navigate("/story-mode"));
    }
}