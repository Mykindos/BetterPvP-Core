package net.betterpvp.core.client;

import org.bukkit.ChatColor;

public enum Rank {

    PLAYER("Player", ChatColor.YELLOW, 0),
    GRIEF("Vacuum Cleaner", ChatColor.WHITE, 1),
    MEDIA("Y&4&T", ChatColor.WHITE, 2),
    BUILDER("Builder", ChatColor.GREEN, 3),
    HELPER("Helper", ChatColor.DARK_GREEN, 4),
    TRIAL_MOD("Trial Mod", ChatColor.DARK_AQUA, 5),
    MODERATOR("Mod", ChatColor.AQUA, 6),
    HEADMOD("Head Mod", ChatColor.AQUA, 7),
    ADMIN("Admin", ChatColor.RED, 8),
    OWNER("Owner", ChatColor.DARK_RED, 9),
    DEVELOPER("", ChatColor.WHITE, 10);

    private final String name;
    private final ChatColor color;
    private final int id;

    Rank(String name, ChatColor color, int id) {
        this.name = name;
        this.color = color;
        this.id = id;
    }

    public int toInt() {
        return this.id;
    }

    public String getTag(boolean bold) {
        String tag = this.name;
        if (bold) {
            return this.color.toString() + ChatColor.BOLD + fixColors(tag);
        }
        return this.color + fixColors(tag);
    }

    private String fixColors(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public String getColor() {
        return this.color.toString();
    }

    public static Rank getRank(int id) {
        for (Rank rank : Rank.values()) {
            if (rank.toInt() == id) {
                return rank;
            }
        }
        return null;
    }
}
