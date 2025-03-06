package com.example.application.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.dom.Element;

@Tag("div")
// Externe CSS-Datei für das Styling
public class GamifiedProgressBar extends Component implements HasSize, HasStyle {

    private final Element progressBar;

    public GamifiedProgressBar(double min, double max, double progress) {
        if (min >= max) {
            throw new IllegalArgumentException(String.format("min ('%s') must be less than max ('%s')", min, max));
        }

        setMin(min);
        setMax(max);

        // Container für den Fortschrittsbalken
        getElement().getStyle()
                .set("width", "100%")
                .set("height", "10px")
                .set("border-radius", "5px")
                .set("position", "relative")
                .set("overflow", "hidden")
                .set("background", "linear-gradient(to right, rgba(139,0,0,0.3), rgba(255,165,0,0.3), rgba(255,255,0,0.3), rgba(144,238,144,0.3))");

        // Fortschrittsbalken (mit voller Farbe)
        progressBar = new Element("div");
        progressBar.getStyle()
                .set("height", "100%")
                .set("width", "100%")
                .set("border-radius", "5px")
                .set("position", "relative")
                .set("top", "0")
                .set("left", "0")
        .set("background", "linear-gradient(to right, rgba(139,0,0), rgba(255,165,0), rgba(255,255,0), rgba(144,238,144))");


        setProgress(progress);

        getElement().appendChild(progressBar);
    }

    public void setProgress(double value) {
        double min = getMin();
        double max = getMax();
        if (value < min || value > max) {
            throw new IllegalArgumentException(String.format("Progress must be between min ('%s') and max ('%s')", min, max));
        }

        double percentage = ((value - min) / (max - min)) * 100;
        progressBar.getStyle().set("width", percentage + "%");


    }

    public void setMax(double max) {
        getElement().setProperty("max", max);
    }

    public double getMax() {
        return getElement().getProperty("max", 0.0);
    }

    public void setMin(double min) {
        getElement().setProperty("min", min);
    }

    public double getMin() {
        return getElement().getProperty("min", 0.0);
    }
}
