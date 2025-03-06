package com.example.application.views.bigtask;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.component.richtexteditor.RichTextEditor;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;

@PageTitle("Big Task")
//@Route("")
//@Menu(order = 0, icon = "line-awesome/svg/pencil-ruler-solid.svg")
public class BigTaskView extends Composite<VerticalLayout> {

    public BigTaskView() {
        VerticalLayout layoutColumn2 = new VerticalLayout();
        H2 h2 = new H2();
        Paragraph textLarge = new Paragraph();
        RichTextEditor richTextEditor = new RichTextEditor();
        TextArea textArea = new TextArea();
        Paragraph textSmall = new Paragraph();
        ProgressBar progressBar = new ProgressBar();
        HorizontalLayout layoutRow = new HorizontalLayout();
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        layoutColumn2.setWidthFull();
        getContent().setFlexGrow(1.0, layoutColumn2);
        layoutColumn2.setWidth("100%");
        layoutColumn2.getStyle().set("flex-grow", "1");
        h2.setText("Heading");
        h2.setWidth("max-content");
        textLarge.setText(
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.");
        textLarge.setWidth("100%");
        textLarge.getStyle().set("font-size", "var(--lumo-font-size-xl)");
        richTextEditor.setWidth("100%");
        textArea.setLabel("Text area");
        textArea.setWidth("100%");
        textSmall.setText(
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.");
        textSmall.setWidth("100%");
        textSmall.getStyle().set("font-size", "var(--lumo-font-size-xs)");
        progressBar.setValue(0.5);
        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.setHeight("min-content");
        getContent().add(layoutColumn2);
        layoutColumn2.add(h2);
        layoutColumn2.add(textLarge);
        layoutColumn2.add(richTextEditor);
        layoutColumn2.add(textArea);
        getContent().add(textSmall);
        getContent().add(progressBar);
        getContent().add(layoutRow);
    }
}
