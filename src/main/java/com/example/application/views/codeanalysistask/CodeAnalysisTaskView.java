package com.example.application.views.codeanalysistask;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Code Analysis Task")
@Route("code-analysis")
public class CodeAnalysisTaskView extends Composite<VerticalLayout> {

    public CodeAnalysisTaskView() {
        H2 title = new H2("Code Analysis Task");
        Paragraph instruction = new Paragraph("Analyze the following code snippet and describe its behavior:");
        String codeSnippet = "Sample CodeSnippet";
        String expectedBehavior = "none";
        TextArea codeArea = new TextArea("Code Snippet");
        codeArea.setValue(codeSnippet);
        codeArea.setReadOnly(true); // Make the code snippet read-only

        TextArea analysisArea = new TextArea("Your Analysis");
        analysisArea.setPlaceholder("Type your analysis here...");
        analysisArea.setMinHeight("150px");

        Button submitButton = new Button("Submit", event -> {
            String studentAnalysis = analysisArea.getValue();
            // Handle logic for checking the student's analysis against expected behavior
            // For example, compare studentAnalysis with expectedBehavior
        });

        getContent().add(title, instruction, codeArea, analysisArea, submitButton);
    }
}
