package com.quiz.model;

public enum Category {
    ANGLAIS("Anglais", "🇬🇧"),
    MATHS("Mathématiques", "🔢"),
    CHIMIE("Chimie", "⚗️");

    private final String label;
    private final String icon;

    Category(String label, String icon) {
        this.label = label;
        this.icon = icon;
    }

    public String getLabel() { return label; }
    public String getIcon() { return icon; }
}
