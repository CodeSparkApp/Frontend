package com.example.application.views.programmingtask;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Programming Task")
@Route("programming-task")
public class ProgrammingTaskView extends Composite<VerticalLayout> {

    public ProgrammingTaskView() {
        H2 title = new H2("Programming Task");
        Paragraph instruction = new Paragraph("Write a complete Java program that fulfills the following requirement:");

        // Example problem statement
        Paragraph problemStatement = new Paragraph(
                "Write a Java program that takes an integer as input and prints whether it is even or odd.");

        // Text area for students to write their code
        TextArea codeEditor = new TextArea("Your Java Code");
        codeEditor.setPlaceholder("Write your Java code here...");
        codeEditor.setWidthFull();
        codeEditor.setHeight("300px");

        // Submit button
        Button submitButton = new Button("Submit", event -> {
            String studentCode = codeEditor.getValue();
            // Future: Logic to validate or execute the submitted Java code
            System.out.println("Submitted Code:\n" + studentCode);
        });

        getContent().add(title, instruction, problemStatement, codeEditor, submitButton);
    }
}
