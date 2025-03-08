package com.example.application.components;

import com.vaadin.collaborationengine.CollaborationMessageInput;
import com.vaadin.collaborationengine.CollaborationMessageList;
import com.vaadin.collaborationengine.UserInfo;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.UUID;

@PageTitle("Chat Input Dialog")
@Route("chat-input")
public class ChatInputDialog extends Dialog {
    public ChatInputDialog() {
        // Benutzerinformationen erstellen
        UserInfo userInfo = new UserInfo(UUID.randomUUID().toString(), "Steve Lange");

        // Titel
        H3 title = new H3("AI-Assistent");

        // Nachrichtenliste
        CollaborationMessageList messageList = new CollaborationMessageList(userInfo, "chat/general");
        messageList.setSizeFull();

        // Texteingabe für Nachrichten
        CollaborationMessageInput messageInput = new CollaborationMessageInput(messageList);
        messageInput.setWidthFull();

        // Layout hinzufügen
        VerticalLayout layout = new VerticalLayout(title, messageList, messageInput);
        layout.setSizeFull();
        layout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.STRETCH);
        layout.setFlexGrow(1, messageList);

        add(layout);
        setWidth("400px");
        setHeight("400px");
    }
}
