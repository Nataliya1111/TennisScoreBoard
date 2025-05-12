package com.nataliya.model;

public enum Points {
    LOVE("0"),
    FIFTEEN("15"),
    THIRTY("30"),
    FORTY("40"),
    ADVANTAGE("AD"),
    DEUCE("40"),
    GAME("game");

    private String label;

    Points(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}
