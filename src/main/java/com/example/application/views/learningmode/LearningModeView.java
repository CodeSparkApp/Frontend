package com.example.application.views.learningmode;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Learning Mode")
@Route("learning-mode")
@Menu(order = 2, icon = "line-awesome/svg/book-solid.svg")
public class LearningModeView extends VerticalLayout {

//    private QuizService quizService = new QuizService();
//    private int currentQuestionIndex = 0;
//    private TextField questionField = new TextField("Question");
//    private Button nextButton = new Button("Next");
//
//
//    public LearningModeView() {
//        setupQuiz();
//        setSpacing(true);
//        setPadding(true);
//
//        H2 title = new H2("Story Mode");
//        add(title);
//        add(questionField, nextButton);
//
//        nextButton.addClickListener(e -> showNextQuestion());
//    }
//
//    private void setupQuiz() {
//        if (!quizService.getQuestions().isEmpty()) {
//            questionField.setValue(quizService.getQuestion(currentQuestionIndex));
//        }
//    }
//
//    private void showNextQuestion() {
//        currentQuestionIndex++;
//        if (currentQuestionIndex < quizService.getQuestions().size()) {
//            questionField.setValue(quizService.getQuestion(currentQuestionIndex));
//        } else {
//            questionField.setValue("Quiz completed!");
//            nextButton.setEnabled(false);
//        }
//    }

    public LearningModeView() {
        Button startQuizButton = new Button("Start Quiz", event -> getUI().ifPresent(ui -> ui.navigate("learning-mode/quiz-selection")));
        Button viewTopicsButton = new Button("View Topics", event -> getUI().ifPresent(ui -> ui.navigate("learning-mode/topic-overview")));

        add(startQuizButton, viewTopicsButton);
    }
}

@Route("learning-mode/quiz-selection")
class QuizSelectionView extends VerticalLayout {
    public QuizSelectionView() {
        Button shortQuizButton = new Button("Short Quiz", event -> getUI().ifPresent(ui -> ui.navigate("learning-mode/short-quiz")));
        Button longQuizButton = new Button("Long Quiz", event -> getUI().ifPresent(ui -> ui.navigate("learning-mode/long-quiz")));
        Button skipQuizButton = new Button("Skip Quiz", event -> getUI().ifPresent(ui -> ui.navigate("learning-mode/topic-overview")));

        add(shortQuizButton, longQuizButton, skipQuizButton);
    }
}



@Route("learning-mode/long-quiz")
class LongQuizView extends VerticalLayout {
    public LongQuizView() {
        TextField codeEditor = new TextField("Write your code here");
        Button submitButton = new Button("Submit", event -> getUI().ifPresent(ui -> ui.navigate("learning-mode/topic-overview")));

        add(codeEditor, submitButton);
    }
}



@Route("learning-mode/task-view")
class TaskView extends VerticalLayout {
    public TaskView() {
        TextField taskDescription = new TextField("Task Description");
        Button completeTaskButton = new Button("Complete Task", event -> getUI().ifPresent(ui -> ui.navigate("learning-mode/topic-overview")));

        add(taskDescription, completeTaskButton);
    }

}
