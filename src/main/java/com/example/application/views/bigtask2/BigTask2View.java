package com.example.application.views.bigtask2;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.checkbox.CheckboxGroupVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Big Task2")
@Route("2")
@Menu(order = 1, icon = "line-awesome/svg/pencil-ruler-solid.svg")
public class BigTask2View extends Composite<VerticalLayout> {

    public BigTask2View() {
        H2 h2 = new H2();
        Paragraph textLarge = new Paragraph();
        CheckboxGroup checkboxGroup = new CheckboxGroup();
        getContent().setHeightFull();
        getContent().setWidthFull();
        h2.setText("Heading");
        h2.setWidth("max-content");
        textLarge.setText(
                "Lorem ipum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.");
        textLarge.setWidth("100%");
        textLarge.getStyle().set("font-size", "var(--lumo-font-size-xl)");
        checkboxGroup.setLabel("Checkbox Group");
        checkboxGroup.setWidth("min-content");
        checkboxGroup.setItems("Order ID", "Product Name", "Customer", "Status");
        checkboxGroup.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);
        getContent().add(h2);
        getContent().add(textLarge);
        getContent().add(checkboxGroup);
    }
}
