package com.example.application.views.storymode;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Story Mode")
@Route("story-mode")
@Menu(order = 2, icon = "line-awesome/svg/book-solid.svg")
public class StoryModeView extends VerticalLayout {

    public StoryModeView() {
        setSpacing(true);
        setPadding(true);

        H2 title = new H2("Story Mode");
        add(title);

        // Placeholder chapters (these should be loaded dynamically)
        String[] chapters = {"Introduction to Java", "Object-Oriented Programming", "Data Structures", "Algorithms"};

        for (String chapter : chapters) {
            Button chapterButton = new Button(chapter, e -> openChapter(chapter));
            chapterButton.setWidthFull();
            add(chapterButton);
        }
    }

    private void openChapter(String chapterTitle) {
        getUI().ifPresent(ui -> ui.navigate("chapter/" + chapterTitle.replace(" ", "-")));
    }
}

