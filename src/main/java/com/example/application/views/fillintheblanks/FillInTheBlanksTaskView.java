package com.example.application.views.fillintheblanks;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.HashMap;
import java.util.Map;

@PageTitle("Fill in the Blanks Task")
@Route("fill-in-the-blanks")
public class FillInTheBlanksTaskView extends Composite<VerticalLayout> {

    private final Map<TextField, String> answerFields = new HashMap<>();

    public FillInTheBlanksTaskView() {
        H2 title = new H2("Fill in the Blanks Task");
        Paragraph instruction = new Paragraph("Complete the missing parts of the following code:");

        // Example: Java code snippet with placeholders
        String[] codeParts = {
                "public class Main {",
                "    public static void main(String[] args) {",
                "        System.out.println(", // missing part
                "        );",
                "    }",
                "}"
        };

        VerticalLayout codeLayout = new VerticalLayout();
        for (String part : codeParts) {
            if (part.contains("System.out.println(")) {
                TextField blankField = new TextField();
                blankField.setPlaceholder("Fill in the blank...");
                answerFields.put(blankField, "\"Hello, World!\"");
                codeLayout.add(blankField);
            } else {
                codeLayout.add(new Paragraph(part));
            }
        }

        Button submitButton = new Button("Submit", event -> checkAnswers());

        getContent().add(title, instruction, codeLayout, submitButton);
    }

    private void checkAnswers() {
        boolean allCorrect = true;
        for (Map.Entry<TextField, String> entry : answerFields.entrySet()) {
            if (!entry.getKey().getValue().equals(entry.getValue())) {
                entry.getKey().setInvalid(true);
                entry.getKey().setErrorMessage("Incorrect answer!");
                allCorrect = false;
            } else {
                entry.getKey().setInvalid(false);
            }
        }
        if (allCorrect) {
            new Paragraph("Correct! Well done.").getElement().executeJs("alert('All answers are correct!')");
        }
    }
}
