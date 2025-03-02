package com.example.application.services;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

public class ExamTimerService {

    private LocalDate examDate;

    public void openExamTimerDialog(Span examTimer) {
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Klausur-Termin auswählen");

        DatePicker datePicker = new DatePicker("Wähle dein Klausur-Datum");
        if (examDate != null) {
            datePicker.setValue(examDate);
        }

        Button confirmButton = new Button("Termin setzen", event -> {
            Optional.ofNullable(datePicker.getValue()).ifPresent(selectedDate -> {
                examDate = selectedDate;
                updateTimerDisplay(examTimer);
            });
            dialog.close();
        });

        VerticalLayout layout = new VerticalLayout(datePicker, confirmButton);
        dialog.add(layout);
        dialog.open();
    }

    public void updateTimerDisplay(Span examTimer) {
        if (examDate != null) {
            LocalDate today = LocalDate.now();
            long daysRemaining = ChronoUnit.DAYS.between(today, examDate);
            examTimer.setText("Zeit bis zur Klausur: " + daysRemaining + " Tage verbleibend");
        }
    }
}
