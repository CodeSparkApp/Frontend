package com.example.application.views.achievements;

import com.example.application.components.GamifiedProgressBar;
import com.example.application.components.OwnProgressBar;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.List;

@Route("/achievements")
@PageTitle("Achievements")
@Menu(order = 5, icon = "line-awesome/svg/trophy-solid.svg")
public class AchievementsView extends VerticalLayout {
    Icon icon = VaadinIcon.TROPHY.create();
    public AchievementsView() {
        List<Badge> badges = BadgeService.getBadges();
        double earnedPercentage = BadgeService.getEarnedPercentage();

        Span progressText = new Span("Achievements: " + String.format("%.0f", earnedPercentage) + "%");
        progressText.getStyle().set("font-weight", "bold").set("margin-bottom", "10px");

        add(progressText);
        add(new GamifiedProgressBar(0, 100, earnedPercentage));

        // Flexbox-Container für das Badge-Grid
        FlexLayout badgeGrid = new FlexLayout();
        badgeGrid.getStyle()
                .set("display", "flex")
                .set("flex-wrap", "wrap")
                .set("justify-content", "center")
                .set("gap", "16px");

        // Badges hinzufügen
        for (Badge badge : badges) {
            badgeGrid.add(createBadgeComponent(badge));
        }

        add(badgeGrid);
    }

    private Div createBadgeComponent(Badge badge) {
        Div badgeContainer = new Div();
//        badgeContainer.getStyle().set("display", "flex")
//                .set("flex-wrap", "wrap")
//                .set("gap", "16px");

         badgeContainer.getStyle().set("display", "inline-block").set("text-align", "center").set("margin", "10px").set("padding", "10px").set("border-radius", "15px").set("box-shadow", "2px 2px 5px rgba(100, 0, 0, 0.1)").set("background", "hsl(0, 0%, 25%)");

        Image badgeImage = new Image(badge.getImagePath(), badge.getName());
        badgeImage.getStyle().set("width", "70px").set("height", "70px").set("border-radius", "5%").set("border", "2px solid #ddd");

        if (!badge.isEarned()) {
            badgeImage.getStyle().set("filter", "grayscale(100%)").set("opacity", "0.5");
            Image lockIcon = new Image("icons/lock.png", "Locked");
            lockIcon.getStyle().set("width", "20px").set("height", "20px").set("position", "absolute").set("top", "50px").set("left", "50px");
            Div imageWrapper = new Div(badgeImage, lockIcon);
            imageWrapper.getStyle().set("position", "relative").set("display", "inline-block");
            badgeContainer.add(imageWrapper);
        } else {
            badgeContainer.add(badgeImage);
        }

        Span badgeName = new Span(badge.getName());
        badgeName.getStyle().set("display", "block").set("margin-top", "5px").set("font-size", "14px").set("font-weight", "bold");
        Div hoverInfo = new Div(new Text(badge.getRequirement()));
        hoverInfo.getStyle().set("display", "none").set("position", "absolute").set("background", "#333").set("color", "white").set("padding", "5px").set("border-radius", "5px").set("font-size", "12px");
        badgeContainer.getElement().addEventListener("mouseover", e -> hoverInfo.getStyle().set("display", "block"));
        badgeContainer.getElement().addEventListener("mouseout", e -> hoverInfo.getStyle().set("display", "none"));

        badgeContainer.add(badgeName, hoverInfo);
        return badgeContainer;
    }
}

class Badge {
    private String name;
    private String imagePath;
    private boolean earned;
    private String requirement;

    public Badge(String name, String imagePath, boolean earned, String requirement) {
        this.name = name;
        this.imagePath = imagePath;
        this.earned = earned;
        this.requirement = requirement;
    }

    public String getName() { return name; }
    public String getImagePath() { return imagePath; }
    public boolean isEarned() { return earned; }
    public String getRequirement() { return requirement; }
}

class BadgeService {
    public static List<Badge> getBadges() {
        return List.of(
                //new Badge("Starter", "icons/Badge_10.png", true, "Complete your first task"),
                new Badge("Expert", "icons/trophy_1.png", true, "Complete 50 tasks"),
                new Badge("Achiever", "icons/trophy_2.png", true, "Complete 10 tasks"),
                new Badge("Master", "icons/trophy_3.png", false, "Complete 100 tasks"),
                new Badge("Speedster", "icons/trophy_4.png", false, "Complete a task in under 1 minute"),
                new Badge("Perfectionist", "icons/trophy_5.png", true, "Complete 5 tasks in a row without mistakes"),
                new Badge("Dedicated", "icons/trophy_6.png", false, "Solve at least one task per day for 7 days"),
                new Badge("Strategist", "icons/trophy_7.png", true, "Use a hint and still solve the task correctly"),
                new Badge("Insomniac", "icons/trophy_8.png", false, "Solve a task between 2 AM and 5 AM")
        );
    }

    public static double getEarnedPercentage() {
        List<Badge> badges = getBadges();
        long earnedCount = badges.stream().filter(Badge::isEarned).count();
        return (double) earnedCount / badges.size() * 100;
    }
}
