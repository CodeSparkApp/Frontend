package com.example.application.views.learningmode;

import com.example.application.services.QuizService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Learning Mode")
@Route("learning-mode")
@Menu(order = 2, icon = "line-awesome/svg/book-solid.svg")
public class LearningModeView extends VerticalLayout {

    private QuizService quizService = new QuizService();
    private int currentQuestionIndex = 0;
    private TextField questionField = new TextField("Question");
    private Button nextButton = new Button("Next");


    public LearningModeView() {
        setupQuiz();
        setSpacing(true);
        setPadding(true);

        H2 title = new H2("Story Mode");
        add(title);
        add(questionField, nextButton);

        nextButton.addClickListener(e -> showNextQuestion());
    }

    private void setupQuiz() {
        if (!quizService.getQuestions().isEmpty()) {
            questionField.setValue(quizService.getQuestion(currentQuestionIndex));
        }
    }

    private void showNextQuestion() {
        currentQuestionIndex++;
        if (currentQuestionIndex < quizService.getQuestions().size()) {
            questionField.setValue(quizService.getQuestion(currentQuestionIndex));
        } else {
            questionField.setValue("Quiz completed!");
            nextButton.setEnabled(false);
        }
    }
}
