package com.example.application.components;


import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class OwnProgressBar extends VerticalLayout {

    private Div progressBarContainer;
    private Div progressBarIndicator;

    public OwnProgressBar() {
        // Set up the container and indicator
        progressBarContainer = new Div();
        progressBarIndicator = new Div();

        // Style for the container
        progressBarContainer.getStyle()
                .set("width", "100%")
                .set("height", "10px")
                .set("border-radius", "5px")
                .set("position", "relative")
                .set("overflow", "hidden")
                .set("background", "linear-gradient(to right, rgba(139,0,0,0.3), rgba(255,165,0,0.3), rgba(255,255,0,0.3), rgba(144,238,144,0.3))");


        // Style for the progress bar indicator
        progressBarIndicator.getStyle().set("height", "100%")
                .set("background-image", "linear-gradient(to right, rgba(139,0,0), rgba(255,165,0), rgba(255,255,0), rgba(144,238,144))")
                .set("background-size", "10000 px 100%")
                .set("border-radius", "25px");

        // Add the indicator to the container
        progressBarContainer.add(progressBarIndicator);

        // Add the container to the layout
        add(progressBarContainer);
    }

    public void setProgress(double progress) {
        // Validate progress and set the width for the indicator
        if (progress >= 0 && progress <= 100) {
            progressBarIndicator.getStyle().set("width", progress + "%");

        }
    }

    public void setCustomColorGradient(String gradient) {
        // Method to customize the gradient if needed
        progressBarIndicator.getStyle().set("background-image", gradient);
    }
}
