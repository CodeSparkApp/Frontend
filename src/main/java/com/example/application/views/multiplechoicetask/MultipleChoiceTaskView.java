package com.example.application.views.multiplechoicetask;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.checkbox.CheckboxGroupVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.List;

@PageTitle("Multiple Choice Task")
@Route("multiple-choice")
public class MultipleChoiceTaskView extends Composite<VerticalLayout> {

    public MultipleChoiceTaskView(List<String> options) {
    //public MultipleChoiceTaskView(String question, List<String> options) {
        H2 title = new H2("Multiple Choice Question");
        String question = "Sample Question";
        Paragraph questionText = new Paragraph(question);

        CheckboxGroup<String> checkboxGroup = new CheckboxGroup<>();
        checkboxGroup.setLabel("Select your answer(s):");
        checkboxGroup.setItems(options);
        checkboxGroup.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);

        Button submitButton = new Button("Submit", event -> {
            // Handle submission logic here
            List<String> selectedAnswers = checkboxGroup.getSelectedItems().stream().toList();
            // You could check the selectedAnswers against the correct answers here
        });

        getContent().add(title, questionText, checkboxGroup, submitButton);
    }
}
