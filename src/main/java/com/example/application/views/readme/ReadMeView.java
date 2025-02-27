package com.example.application.views.readme;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Tutorial")
@Route("")
@Menu(order = 1, icon = "line-awesome/svg/pencil-ruler-solid.svg")
public class ReadMeView extends Composite<VerticalLayout> {

    public ReadMeView() {
        H2 title = new H2("📚 CodeSpark - Benutzerhandbuch");
        Paragraph intro = new Paragraph("Willkommen zur offiziellen Dokumentation der Lernplattform! Diese Plattform unterstützt Studierende beim gezielten Lernen und der Vorbereitung auf Prüfungen durch verschiedene interaktive Modi.");

        H2 featuresTitle = new H2("✨ Features");
        Paragraph features = new Paragraph("- Story Mode: Kapitelbasierte Lernreise mit Theorie und Übungen.\n" +
                "- Learning Mode: Adaptive Aufgabenauswahl basierend auf Leistungsanalyse.\n" +
                "- KI-gestützte Aufgabenempfehlung: Dynamische Gewichtung basierend auf Erfolgsrate.\n" +
                "- Fortschrittsanzeige: Visuelle Darstellung des Lernerfolgs mit Badges und Rankings.\n" +
                "- Vielfältige Aufgabentypen: Programmierung, Debugging, Multiple Choice, Code-Analyse uvm.");

        H2 navigationTitle = new H2("📌 Navigation");
        Paragraph navigation = new Paragraph("Die Plattform ist in mehrere Hauptbereiche unterteilt:\n\n" +
                "- Story Mode: Arbeiten Sie Kapitel in fester Reihenfolge durch.\n" +
                "- Learning Mode: Beim ersten Besuch erfolgt ein Quiz zur Einstufung.\n" +
                "- Vielfältige Aufgabentypen wie Programmieraufgaben, Debugging und Multiple-Choice.");

        H2 contactTitle = new H2("📩 Kontakt & Feedback");
        Paragraph contact = new Paragraph("Haben Sie Fragen oder Anregungen? Kontaktieren Sie uns unter support@lernplattform.de.");

        getContent().add(title, intro, featuresTitle, features, navigationTitle, navigation, contactTitle, contact);
    }
}
