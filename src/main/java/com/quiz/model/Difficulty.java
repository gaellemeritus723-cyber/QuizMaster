package com.quiz.model;

public enum Difficulty {
    FACILE("Facile", "success"),
    MOYEN("Moyen", "warning"),
    DIFFICILE("Difficile", "danger");

    private final String label;
    private final String badgeClass;

    Difficulty(String label, String badgeClass) {
        this.label = label;
        this.badgeClass = badgeClass;
    }

    public String getLabel() { return label; }
    public String getBadgeClass() { return badgeClass; }
}
