package com.example.application.views.achievements;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.router.Route;
import java.util.List;

@Route("/achievements")
public class AchievementsView extends VerticalLayout {

    public AchievementsView() {
        List<Badge> badges = BadgeService.getBadges();
        double earnedPercentage = BadgeService.getEarnedPercentage();

        // Fortschrittsanzeige mit visuellem Balken
        Div progressContainer = new Div();
        progressContainer.getStyle().set("width", "100%").set("background", "#eee").set("border-radius", "10px").set("margin-bottom", "20px");

        Div progressBar = new Div();
        progressBar.getStyle().set("width", earnedPercentage + "%").set("background", "#4CAF50").set("height", "20px").set("border-radius", "10px");
        progressContainer.add(progressBar);

        Span progressText = new Span("Achievements: " + String.format("%.0f", earnedPercentage) + "%");
        progressText.getStyle().set("font-weight", "bold").set("margin-bottom", "10px");

        add(progressText, progressContainer);

        // Anzeige der Badges
        for (Badge badge : badges) {
            add(createBadgeComponent(badge));
        }
    }

    private Div createBadgeComponent(Badge badge) {
        Div badgeContainer = new Div();
        badgeContainer.getStyle().set("display", "inline-block").set("text-align", "center").set("margin", "10px").set("padding", "10px").set("border-radius", "15px").set("background", "#f0f0f0").set("box-shadow", "2px 2px 5px rgba(0, 0, 0, 0.1)");

        Image badgeImage = new Image(badge.getImagePath(), badge.getName());
        badgeImage.getStyle().set("width", "70px").set("height", "70px").set("border-radius", "5%").set("border", "2px solid #ddd");

        if (!badge.isEarned()) {
            badgeImage.getStyle().set("filter", "grayscale(100%)").set("opacity", "0.5");
            Image lockIcon = new Image("images/lock.png", "Locked");
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
                new Badge("Starter", "icons/Badge_10.png", true, "Complete your first task"),
                new Badge("Expert", "images/expert.png", false, "Complete 50 tasks")
        );
    }

    public static double getEarnedPercentage() {
        List<Badge> badges = getBadges();
        long earnedCount = badges.stream().filter(Badge::isEarned).count();
        return (double) earnedCount / badges.size() * 100;
    }
}
