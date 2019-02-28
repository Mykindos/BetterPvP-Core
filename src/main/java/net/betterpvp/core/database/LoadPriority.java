package net.betterpvp.core.database;

public enum LoadPriority {

    // Lowest = Load first, Highest = Load last
    LOWEST(1), LOW(2), MEDIUM(3), HIGH(4), HIGHEST(5);

    private int priority;

    LoadPriority(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

}
