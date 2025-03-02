package com.example.application.views;

import com.example.application.components.UserProfileDialog;
import com.example.application.services.ExamTimerService;
import com.example.application.data.User;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.SvgIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.popover.Popover;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.Layout;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.server.menu.MenuConfiguration;
import com.vaadin.flow.server.menu.MenuEntry;
import com.vaadin.flow.theme.lumo.LumoUtility;

import java.util.List;

@Layout
@AnonymousAllowed
public class MainLayout extends AppLayout {

    private H1 viewTitle;
    private Span examTimer;
    private final ExamTimerService examTimerService = new ExamTimerService();
    private final User currentUser;

    public MainLayout() {
        this.currentUser = fetchCurrentUser(); // Methode, um aktuellen Benutzer zu laden
        setPrimarySection(Section.DRAWER);
        addDrawerContent();
        addHeaderContent();
    }

    private User fetchCurrentUser() {
        return new User(1, "MaxSchwinghammer", "max@example.com", "https://tenor.com/view/epic-gif-spiderman-farts-farting-fart-gif-26232700", "{}", "{}", "🏆 Gold-Badge, 🥈 Silber-Badge", null);
    }

    private void addHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.setAriaLabel("Menu toggle");

        viewTitle = new H1();
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);

        Avatar avatar = new Avatar(currentUser.getUsername());
        avatar.setImage(currentUser.getAvatar());
        Span userName = new Span(currentUser.getUsername());

        HorizontalLayout profileLayout = new HorizontalLayout(avatar, userName);
        profileLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        profileLayout.addClassNames(LumoUtility.Margin.Left.AUTO, LumoUtility.Padding.SMALL);

        profileLayout.addClickListener(spanClickEvent -> {
            UserProfileDialog dialog = new UserProfileDialog(currentUser);
            dialog.open();
        });

        examTimer = new Span("Klausurtermin festlegen");
        examTimer.addClassNames(LumoUtility.FontSize.MEDIUM, LumoUtility.Margin.Right.MEDIUM, LumoUtility.Margin.Left.AUTO);
        examTimer.addClickListener(event -> examTimerService.openExamTimerDialog(examTimer));

        addToNavbar(true, toggle, viewTitle, examTimer, profileLayout);
    }

    private void addDrawerContent() {
        Span appName = new Span("CodeSpark");
        appName.addClassNames(LumoUtility.FontWeight.SEMIBOLD, LumoUtility.FontSize.LARGE);
        Image logo = new Image("icons/logo.png", "CodeSpark Logo");
        logo.setWidth("50px");
        Header header = new Header(appName, logo);

        Scroller scroller = new Scroller(createNavigation());

        addToDrawer(header, scroller, createFooter());
    }

    private SideNav createNavigation() {
        SideNav nav = new SideNav();

        List<MenuEntry> menuEntries = MenuConfiguration.getMenuEntries();
        menuEntries.forEach(entry -> {
            if (entry.icon() != null) {
                nav.addItem(new SideNavItem(entry.title(), entry.path(), new SvgIcon(entry.icon())));
            } else {
                nav.addItem(new SideNavItem(entry.title(), entry.path()));
            }
        });
        return nav;
    }

    private Footer createFooter() {
        Footer layout = new Footer();
        layout.add(new Span("© 2025 CodeSpark - Alle Rechte vorbehalten."));
        return layout;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        return MenuConfiguration.getPageHeader(getContent()).orElse("");
    }
}
