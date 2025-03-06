package com.example.application.views.debuggingtask;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Debugging Task")
@Route("debugging")
public class DebuggingTaskView extends Composite<VerticalLayout> {

    public DebuggingTaskView() {
        H2 title = new H2("Debugging Task");
        Paragraph instruction = new Paragraph("Identify and correct the errors in the following code:");
        String faultyCode = "sample Faulty Code";
        TextArea codeArea = new TextArea("Faulty Code");
        codeArea.setValue(faultyCode);
        codeArea.setReadOnly(true); // Read-only for the faulty code

        TextArea correctionArea = new TextArea("Your Corrections");
        correctionArea.setPlaceholder("Type your corrected code here...");

        Button submitButton = new Button("Submit", event -> {
            String studentCorrection = correctionArea.getValue();
            // Handle the logic for checking the correction
        });

        getContent().add(title, instruction, codeArea, correctionArea, submitButton);
    }
}
