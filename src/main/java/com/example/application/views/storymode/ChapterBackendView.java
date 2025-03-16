package com.example.application.views.storymode;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Pre;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@PageTitle("Lesson")
@Route("story-mode/lesson/:id")
public class ChapterBackendView extends VerticalLayout implements BeforeEnterObserver {

    private String lessonId;
    //private String lessonName;

    public ChapterBackendView() {
        setSpacing(true);
        setPadding(true);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        lessonId = event.getRouteParameters().get("id").orElse("");
        //lessonName = event.getRouteParameters().get("lessonName").orElse("");

        if (!lessonId.isEmpty()) {
            // API-Aufruf zur Lektionenübersicht mit der lessonId
            loadLessonDetails();
        } else {
            Notification.show("Lesson ID is missing!");
        }
    }

    private void loadLessonDetails() {
        // Die API-URL für den GET-Request mit lessonId
        String apiUrl = "https://codespark.up.railway.app/api/v1/lesson/" + lessonId;

        try {
            VaadinSession session = VaadinSession.getCurrent();
            String accessToken = (String) session.getAttribute("access_token");

            if (accessToken == null) {
                Notification.show("Fehler: Kein Zugriffstoken vorhanden!");
                return;
            }

            // Die HTTP-Verbindung herstellen
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Bearer " + accessToken);
            connection.setRequestProperty("Accept", "application/json");

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                // Antwort verarbeiten
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonResponse = objectMapper.readTree(reader);
                reader.close();


                renderLessonDetails(jsonResponse);



                // Rendern der Lektion Details
                //renderLessonDetails(title, description, type);
            } else {
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                StringBuilder errorMessage = new StringBuilder();
                String line;
                while ((line = errorReader.readLine()) != null) {
                    errorMessage.append(line);
                }
                errorReader.close();
                Notification.show("Fehler beim Laden der Lektion: " + responseCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
            Notification.show("Ein Fehler ist aufgetreten: " + e.getMessage());
        }
    }

    private void renderLessonDetails(JsonNode jsonResponse) {
        // Lektion Details extrahieren
        String title = jsonResponse.get("title").asText();
        String description = jsonResponse.get("description").asText();
        String type = jsonResponse.get("type").asText();

        // Titel und Beschreibung
        H2 lessonTitle = new H2(title);
        //Paragraph lessonDescription = new Paragraph(description);

        add(lessonTitle);

        switch (type) {
            case "PROGRAMMING" -> {
                String problem = jsonResponse.get("problem").asText();
                String code = jsonResponse.get("code").asText();
                add(new Paragraph(problem));

                //Lösung ist hier drin
                //add(new Paragraph("Code: " + code));

                TextArea codeEditor = new TextArea("Your Java Code");
                codeEditor.setPlaceholder("Write your Java code here...");
                codeEditor.setWidthFull();
                codeEditor.setHeight("300px");

                // Absenden-Button
                Button submitButton = new Button("Submit", event -> {
                    String studentCode = codeEditor.getValue();
                    // Hier kannst du die Logik zum Validieren oder Ausführen des Java-Codes hinzufügen
                    System.out.println("Submitted Code:\n" + studentCode);
                });
                add(codeEditor, submitButton);
            }
            case "DEBUGGING" -> {
                Paragraph lessonDescription = new Paragraph(description);

                String faultyCode = jsonResponse.get("faulty_code").asText();
                String expectedOutput = jsonResponse.get("expected_output").asText();

                // Code als Pre-Element anzeigen (ähnlich wie ein Code-Block)
                Pre codeBlock = new Pre(faultyCode);
                //codeBlock.getStyle().set("background-color", "#f5f5f5");
                codeBlock.getStyle().set("padding", "10px");
                codeBlock.getStyle().set("border-radius", "8px");

                // Textfeld für die Lösung
                TextArea solutionField = new TextArea("Fix the Code");
                solutionField.setPlaceholder("Enter your corrected code here...");

                // Überprüfungs-Button
                Button checkButton = new Button("Submit", event -> {
                    String userCode = solutionField.getValue();
                    if (userCode.contains("/ 0")) {
                        Notification.show("Division by zero is not allowed!");
                    } else {
                        Notification.show("Your code seems fine. Expected Output: " + expectedOutput);
                    }
                });

                add(lessonDescription, codeBlock, solutionField, checkButton);
            }
            case "CODE_ANALYSIS" -> {
                String code = jsonResponse.get("code").asText();
                String question = jsonResponse.get("question").asText();
                Paragraph lessonDescription = new Paragraph(description);
                // Code in einem <pre> und <code>-Block formatieren
                String formattedCode = code.replace("\r\n", "\n");
                TextArea solutionField = new TextArea("Your Answer");
                solutionField.setPlaceholder("Enter your Answer here...");

                add(lessonDescription);
                add(new Pre(formattedCode));  // Hier wird der formatierte Code eingefügt
                add(new Paragraph("Question: " + question));
                add(solutionField);

            }
            case "THEORY" -> {
                String text = jsonResponse.get("text").asText();
                add(new Paragraph("Text: " + text));
            }
            case "MULTIPLE_CHOICE" -> {
                String question = jsonResponse.get("question").asText();
                JsonNode optionsNode = jsonResponse.get("options");

                // RadioButtonGroup für die Optionen
                RadioButtonGroup<String> optionsGroup = new RadioButtonGroup<>();
                optionsGroup.setLabel(question);

                // Extrahiere die Optionen aus dem JSON-Array
                List<String> optionsList = new ArrayList<>();
                if (optionsNode.isArray()) {
                    for (JsonNode option : optionsNode) {
                        optionsList.add(option.asText());
                    }
                }

                // Setze die Optionen in die RadioButtonGroup
                optionsGroup.setItems(optionsList);


                    // Button zum Überprüfen der Antwort
                    Button submitButton = new Button("Submit", event -> {
                        String selectedOption = optionsGroup.getValue();
                        if (selectedOption.isEmpty()) {
                            Notification.show("Please select an option!");
                        } else {
                            Notification.show("You selected: " + selectedOption);
                        }
                    });

                    add(optionsGroup, submitButton);
                }

            case "FILL_BLANKS" -> {
                String templateCode = jsonResponse.get("template_code").asText();
                String expectedOutput = jsonResponse.get("expected_output").asText();
                TextField blank1 = new TextField();
                blank1.setPlaceholder("Fill blank 1");
                TextField blank2 = new TextField();
                blank2.setPlaceholder("Fill blank 2");
                TextField blank3 = new TextField();
                blank3.setPlaceholder("Fill blank 3");
                TextField blank4 = new TextField();
                blank4.setPlaceholder("Fill blank 4");

                Pre codeBlock = new Pre(templateCode);

                Button checkButton = new Button("Submit", event -> {
                    String filledCode = String.format(
                            "try {\n  int x = %s / %s;\n} catch (ArithmeticException e) {\n  System.out.println(\"%s\");\n} finally {\n  System.out.println(\"%s\");\n}",
                            blank1.getValue(), blank2.getValue(), blank3.getValue(), blank4.getValue()
                    );

                    // Beispielhafte Prüfung der erwarteten Ausgabe
                    if ("10".equals(blank1.getValue()) && "0".equals(blank2.getValue()) &&
                            "Division by zero error".equals(blank3.getValue()) && "Finally block executed".equals(blank4.getValue())) {
                        Notification.show("Correct solution!");
                    } else {
                        Notification.show("Incorrect! Try again.");
                    }

                    codeBlock.setText(filledCode);
                });
                VerticalLayout blankLayout = new VerticalLayout(blank1, blank2, blank3, blank4);
                HorizontalLayout horizontalLayout = new HorizontalLayout(codeBlock, blankLayout);
                add(horizontalLayout, checkButton);
                //add(blank1, blank2, blank3, blank4, checkButton, codeBlock);
            }
            default -> Notification.show("Unknown lesson type!");
        }
    }


    private void addProgrammingTask() {
        // Anweisungen für die Aufgabe
        Paragraph instruction = new Paragraph("Write a complete Java program that fulfills the following requirement:");

        // Beispiel für die Problemstellung
        Paragraph problemStatement = new Paragraph(
                "Write a Java program that takes an integer as input and prints whether it is even or odd.");

        // Textbereich für den Code
        TextArea codeEditor = new TextArea("Your Java Code");
        codeEditor.setPlaceholder("Write your Java code here...");
        codeEditor.setWidthFull();
        codeEditor.setHeight("300px");

        // Absenden-Button
        Button submitButton = new Button("Submit", event -> {
            String studentCode = codeEditor.getValue();
            // Hier kannst du die Logik zum Validieren oder Ausführen des Java-Codes hinzufügen
            System.out.println("Submitted Code:\n" + studentCode);
        });

        // Code-Editor und Button zum Layout hinzufügen
        add(instruction, problemStatement, codeEditor, submitButton);
    }
}
