package net.betterpvp.core.utility;

import net.betterpvp.core.client.Rank;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UtilMessage {


    /**
     * Sends a message to a player with appropriate formatting
     *
     * @param player  The player
     * @param prefix  The message
     * @param message Message to send to a player
     */
    public static void message(Player player, String prefix, String message) {
        player.sendMessage(ChatColor.BLUE + prefix + "> " + ChatColor.GRAY + message);
    }

    /**
     * Sends a message to a CommandSender with appropriate formatting
     * Can also send to players
     *
     * @param sender  The CommandSender
     * @param prefix  The message
     * @param message Message to send to the CommandSender
     */
    public static void message(CommandSender sender, String prefix, String message) {
        sender.sendMessage(ChatColor.BLUE + prefix + "> " + ChatColor.GRAY + message);
    }

    /**
     * Sends a message to a player with appropriate formatting
     * Additionally plays a sound to the player when they receive this message
     *
     * @param player  The player
     * @param prefix  The message
     * @param message Message to send to a player
     * @param sound   Whether or not to send a sound to the player as well
     */
    public static void message(Player player, String prefix, String message, boolean sound) {
        if (sound) {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0F, 1.0F);
        }

        player.sendMessage(ChatColor.BLUE + prefix + "> " + ChatColor.GRAY + message);
    }

    /**
     * Sends a message to a player, does not format the message
     *
     * @param player  The player receiving the message
     * @param message The message to be sent
     */
    public static void message(Player player, String message) {
        player.sendMessage(message);
    }


    /**
     * Sends a message to a player, adds the required rank at the end of the message
     *
     * @param player  The player receiving the message
     * @param command The command being executed
     * @param message The message to be sent
     * @param rank    The rank required to use this command
     */
    public static void message(Player player, String command, String message, Rank rank) {
        player.sendMessage(rank.getColor() + command + " " + ChatColor.GRAY + message + rank.getColor() + " " + rank.getTag(false));
    }

    /**
     * Sends an array of strings to a player, does not format the strings
     *
     * @param player  The player receiving the message
     * @param message The strings to be sent
     */
    public static void message(Player player, String[] message) {
        for (String string : message) {
            player.sendMessage(string);
        }
    }

    /**
     * Sends an array of strings to a player with appropriate formatting
     *
     * @param player  The player
     * @param prefix  The message
     * @param message Strings to send to a player
     */
    public static void message(Player player, String prefix, String[] message) {
        for (String string : message) {
            player.sendMessage(ChatColor.BLUE + prefix + "> " + ChatColor.GRAY + string);
        }
    }

    /**
     * Broadcasts a message to all players on the server with formatting
     *
     * @param prefix  The prefix of the message
     * @param message The message to be broadcasted
     */
    public static void broadcast(String prefix, String message) {
        Bukkit.broadcastMessage(ChatColor.BLUE + prefix + "> " + ChatColor.GRAY + message);
    }

    /**
     * Broadcasts a message to all players on the server with formatting
     *
     * @param message The message to be broadcasted
     */
    public static void broadcast(String message) {
        Bukkit.broadcastMessage(ChatColor.GRAY + message);
    }


    /**
     * Gets the final argument of a command
     *
     * @param args  Arguments of a command
     * @param start Argument to start from
     * @return Returns the final command argument
     */
    public static String getFinalArg(String[] args, int start) {
        StringBuilder bldr = new StringBuilder();
        for (int i = start; i < args.length; i++) {
            if (i != start) {
                bldr.append(" ");
            }
            bldr.append(args[i]);
        }
        return bldr.toString();
    }
}
