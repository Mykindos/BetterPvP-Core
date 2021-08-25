package net.betterpvp.core.utility;

import org.bukkit.entity.Player;

public class Titles {

    public static void sendTitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String title, String subtitle) {
        player.sendTitle(title, subtitle, fadeIn, stay, fadeOut);

    }
}
