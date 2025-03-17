package com.example;

import java.util.HashMap;
import java.util.Map;

public enum DifficultyEnum {
    UNTAGGED(0, "none"),
    EASY(1, "easy"),
    NORMAL(2, "norm"),
    DIFFICULT(3, "diff");

    private final int level;
    private final String value;

    DifficultyEnum(int level, String value) {
        this.level = level;
        this.value = value;
    }

    public int getLevel() {
        return level;
    }

    public String getValue() {
        return value;
    }

    private static final Map<Integer, DifficultyEnum> BY_LEVEL = new HashMap<>();
    private static final Map<String, DifficultyEnum> BY_VALUE = new HashMap<>();

    static {
        for (DifficultyEnum e : values()) {
            BY_LEVEL.put(e.level, e);
            BY_VALUE.put(e.value, e);
        }
    }

    public static DifficultyEnum valueOfLevel(int label) {
        return BY_LEVEL.getOrDefault(label, UNTAGGED);
    }

    public static DifficultyEnum valueOfValue(String value) {
        return BY_VALUE.getOrDefault(value, UNTAGGED);
    }
}
