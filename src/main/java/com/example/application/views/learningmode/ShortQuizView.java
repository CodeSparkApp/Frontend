package com.example.application.views.learningmode;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.Arrays;
import java.util.List;

@PageTitle("Short Quiz")
@Route("learning-mode/short-quiz")
public class ShortQuizView extends VerticalLayout {

    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private int correctAnswers = 0;

    private H2 questionTitle = new H2();
    private Paragraph questionText = new Paragraph();
    private RadioButtonGroup<String> options = new RadioButtonGroup<>();
    private Button nextButton = new Button("Next");

    public ShortQuizView() {
        questions = Arrays.asList(
                new Question("What is Java?", "A programming language", Arrays.asList("A fruit", "A car brand", "A programming language")),
                new Question("What is OOP?", "Object-Oriented Programming", Arrays.asList("Object-Oriented Programming", "Only Odd Programs", "Old Operating Procedures")),
                new Question("What does JVM stand for?", "Java Virtual Machine", Arrays.asList("Java Virtual Machine", "Java Version Manager", "Just Visual Memory"))
        );

        setSizeFull();
        setAlignItems(Alignment.CENTER);

        options.setLabel("Choose an answer:");
        nextButton.addClickListener(e -> handleNext());

        add(questionTitle, questionText, options, nextButton);
        loadQuestion();
    }

    private void loadQuestion() {
        if (currentQuestionIndex < questions.size()) {
            Question currentQuestion = questions.get(currentQuestionIndex);
            questionTitle.setText("Question " + (currentQuestionIndex + 1));
            questionText.setText(currentQuestion.getQuestion());
            options.setItems(currentQuestion.getOptions());
            options.clear();
        } else {
            showResults();
        }
    }

    private void handleNext() {
        if (options.getValue() == null) {
            Notification.show("Please select an answer.", 2000, Notification.Position.MIDDLE);
            return;
        }

        if (options.getValue().equals(questions.get(currentQuestionIndex).getCorrectAnswer())) {
            correctAnswers++;
        }

        currentQuestionIndex++;
        loadQuestion();
    }

    private void showResults() {
        removeAll();
        add(new H2("Quiz Completed!"));
        Button continueButton = new Button("Continue");
                continueButton.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("learning-mode/topic-overview")));
        add(new Paragraph("You answered " + correctAnswers + " out of " + questions.size() + " correctly."));
        add(continueButton);
    }

    private static class Question {
        private String question;
        private String correctAnswer;
        private List<String> options;

        public Question(String question, String correctAnswer, List<String> options) {
            this.question = question;
            this.correctAnswer = correctAnswer;
            this.options = options;
        }

        public String getQuestion() {
            return question;
        }

        public String getCorrectAnswer() {
            return correctAnswer;
        }

        public List<String> getOptions() {
            return options;
        }
    }
}
