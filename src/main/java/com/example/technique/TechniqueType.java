package com.example.technique;

/**
 * テクニックの情報を保持するenum.
 */
public enum TechniqueType {
    NAKED_SINGLE(1, "Naked Single"),
    HIDDEN_SINGLE(2, "Hidden Single");

    private final int difficulty;
    private final String techniqueName;

    TechniqueType(int difficulty, String techniqueName) {
        this.difficulty = difficulty;
        this.techniqueName = techniqueName;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public String getTechniqueName() {
        return techniqueName;
    }
}
