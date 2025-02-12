package ru.aston.karpenko_ds.task_data_structures;

public enum TestResult {
    OK("OK"),
    TRY_AGAIN("Please, try again!");

    private final String text;

    TestResult(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
