package com.example.application.views.myview;

import com.example.application.data.user;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;

@PageTitle("My View")
@Route("my-view")
@Menu(order = 2, icon = "line-awesome/svg/pencil-ruler-solid.svg")
public class MyViewView extends Composite<VerticalLayout> {

    public MyViewView() {
        VerticalLayout layoutColumn2 = new VerticalLayout();
        Text text2 = new Text("Hier steht Beispieltext");
        HorizontalLayout layoutRow = new HorizontalLayout();
        Grid<user> storyGrid = new Grid<>(user.class, false);
        storyGrid.addColumn(user::getUserName).setHeader("Rank");
        storyGrid.addColumn(user::getUserName).setHeader("Username");
        storyGrid.addColumn(user::getStoryPoints).setHeader("Points");

        Grid<user> learningGrid = new Grid<>(user.class, false);
        learningGrid.addColumn(user::getUserName).setHeader("Rank");
        learningGrid.addColumn(user::getUserName).setHeader("Username");
        learningGrid.addColumn(user::getStoryPoints).setHeader("Points");
        Grid<user> overallGrid = new Grid<>(user.class, false);
        overallGrid.addColumn(user::getUserName).setHeader("Rank");
        overallGrid.addColumn(user::getUserName).setHeader("Username");
        overallGrid.addColumn(user::getStoryPoints).setHeader("Points");

        Tab tab1 = new Tab("Story Mode ");
        TabSheet tabSheet = new TabSheet();
        tabSheet.add("Story Mode Leaderboard",
                new Div( new Div(storyGrid)));
        tabSheet.add("Learning Mode Leaderboard",
                new Div(new Div(learningGrid)));
        tabSheet.add("Overall Leaderboard",
                new Div(new Div(overallGrid)));
        layoutRow.add(tabSheet);


        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        tabSheet.setWidth("100%");
        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.setHeight("min-content");
        getContent().add(layoutRow);
        layoutColumn2.setWidth("100%");
        layoutColumn2.getStyle().set("flex-grow", "1");
    }
}
