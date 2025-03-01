package com.example.application.views.myview;

import com.example.application.data.User;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;

@PageTitle("Leaderboard")
@Route("leaderboard")
@Menu(order = 4, icon = "line-awesome/svg/pencil-ruler-solid.svg")
public class MyViewView extends Composite<VerticalLayout> {

    public MyViewView() {
        VerticalLayout layoutColumn2 = new VerticalLayout();
        HorizontalLayout layoutRow = new HorizontalLayout();

        Grid<User> overallGrid = new Grid<>(User.class, false);
        overallGrid.addColumn(User::getUsername).setHeader("Rank");
        overallGrid.addColumn(User::getUsername).setHeader("Username");
        overallGrid.addColumn(User::getStoryPoints).setHeader("Points");





        layoutRow.add(overallGrid);


        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.setHeight("min-content");
        getContent().add(layoutRow);
        layoutColumn2.setWidth("100%");
        layoutColumn2.getStyle().set("flex-grow", "1");
    }
}
