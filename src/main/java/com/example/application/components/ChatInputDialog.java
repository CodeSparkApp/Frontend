package com.example.application.components;

import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.messages.MessageInput;
import com.vaadin.flow.component.messages.MessageList;
import com.vaadin.flow.component.messages.MessageListItem;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.component.UI;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@PageTitle("Chat Input Dialog")
@Route("chat-input")
public class ChatInputDialog extends Dialog {
    private static final String API_URL = "https://codespark.up.railway.app/api/v1/openai/prompt-stream";
    private final MessageList messageList;
    private final List<MessageListItem> messages = new ArrayList<>();

    public ChatInputDialog() {
        // Titel
        H3 title = new H3("AI-Assistent");

        // Nachrichtenliste
        messageList = new MessageList();
        messageList.setItems(messages);
        messageList.setSizeFull();

        // Eingabefeld für Nachrichten
        MessageInput messageInput = new MessageInput();
        messageInput.addSubmitListener(event -> {
            String userMessage = event.getValue().trim();
            if (!userMessage.isEmpty()) {
                addMessageToChat("Du", userMessage);
                String accessToken = (String) VaadinSession.getCurrent().getAttribute("access_token");
                sendApiRequest(userMessage, accessToken);
            }
        });

        // Layout zusammenbauen
        VerticalLayout layout = new VerticalLayout(title, messageList, messageInput);
        layout.setSizeFull();
        layout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.STRETCH);
        layout.setFlexGrow(1, messageList);

        add(layout);
        setWidth("60%");
        setDraggable(true);
        setResizable(true);
        setCloseOnEsc(true);
        setHeight("50%");
    }

    private void sendApiRequest(String prompt, String accessToken) {
        if (accessToken == null || accessToken.isEmpty()) {
            addMessageToChat("AI-Assistent", "Fehler: Kein Zugriffstoken gefunden");
            return;
        }

        new Thread(() -> {
            try {
                URL url = new URL(API_URL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Authorization", "Bearer " + accessToken);
                conn.setDoOutput(true);

                // JSON-Body
                String jsonBody = String.format("{\"prompt\": \"%s\"}", prompt);

                // Request senden
                try (OutputStream os = conn.getOutputStream()) {
                    byte[] input = jsonBody.getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }

                // Antwort verarbeiten
                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    try (Scanner scanner = new Scanner(conn.getInputStream(), StandardCharsets.UTF_8)) {
                        StringBuilder responseChunk = new StringBuilder();

                        // Eine initiale Nachricht für den Chat, die später aktualisiert wird
                        MessageListItem messageItem = new MessageListItem("", LocalDateTime.now().minusHours(1).toInstant(ZoneOffset.UTC), "AI-Assistent");

                        // Verwenden von UI.access(), um sicherzustellen, dass der UI-Zugriff im UI-Thread erfolgt
                        getUI().ifPresent(ui -> ui.access(() -> {
                            messages.add(messageItem);  // Leere Nachricht hinzufügen
                            updateMessageList();  // UI aktualisieren
                        }));

                        // Zeichen für Zeichen lesen und in Echtzeit anzeigen
                        while (scanner.hasNext()) {
                            String line = scanner.nextLine(); // Liest das nächste Zeichen
                            responseChunk.append(line); // Zeichen an den Buffer anfügen

                            // Nachricht in der bestehenden MessageItem aktualisieren
                            final String currentResponse = responseChunk.toString(); // Aktuellen Stand holen
                            getUI().ifPresent(ui -> ui.access(() -> {
                                messageItem.setText(currentResponse);  // Nachricht aktualisieren
                                updateMessageList(); // UI aktualisieren
                            }));
                        }
                        scrollToBottom();
                    }
                } else {
                    addMessageToChat("AI-Assistent", "Fehler: " + responseCode);
                }
            } catch (Exception e) {
                addMessageToChat("AI-Assistent", "Fehlgeschlagen: " + e.getMessage());
            }
        }).start();
    }

    private void addMessageToChat(String sender, String message) {
        getUI().ifPresent(ui -> ui.access(() -> {
            messages.add(new MessageListItem(message, LocalDateTime.now().minusHours(1).toInstant(ZoneOffset.UTC), sender));
            updateMessageList();
        }));
    }

    private void updateMessageList() {
        getUI().ifPresent(ui -> ui.access(() -> {
            messageList.setItems(new ArrayList<>(messages));  // UI aktualisieren
            scrollToBottom();
        }));
    }

    private void scrollToBottom() {
        getUI().ifPresent(ui -> ui.access(() -> {
            messageList.getElement().executeJs("this.scrollTo(0, this.scrollHeight);");
        }));
    }
}