package com.example.application.tasks;

public class DebuggingTask extends Task {
    private String faultyCode; // The code with errors
    private String correctionHint; // Hint for correction

    public DebuggingTask(int id, String description, String faultyCode, String correctionHint) {
        super(id, description, "Debugging");
        this.faultyCode = faultyCode;
        this.correctionHint = correctionHint;
    }

    @Override
    public boolean solve() {
        // Logic to check if the code has been correctly debugged
        return false; // Example value
    }

    public String getFaultyCode() {
        return faultyCode;
    }

    public String getCorrectionHint() {
        return correctionHint;
    }
}
