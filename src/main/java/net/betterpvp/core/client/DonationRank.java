package net.betterpvp.core.client;

import org.bukkit.ChatColor;

public enum DonationRank {

    LEGACY("Legacy", ChatColor.LIGHT_PURPLE, 0),
    SAVIOR("Savior", ChatColor.GREEN, 1),
    DIVINE("Divine", ChatColor.DARK_AQUA, 2),
    GUARDIAN("Guardian", ChatColor.AQUA, 3);


    private final String name;
    private final ChatColor color;
    private final int id;

    DonationRank(String name, ChatColor color, int id) {
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

    public int getId() {
        return id;
    }

    private String fixColors(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public String getColor() {
        return this.color.toString();
    }

    public static DonationRank getRank(int id) {
        for (DonationRank rank : DonationRank.values()) {
            if (rank.toInt() == id) {
                return rank;
            }
        }
        return null;
    }
}
