package net.betterpvp.core.client.commands;

import net.betterpvp.core.client.Rank;
import net.betterpvp.core.command.Command;
import net.betterpvp.core.utility.Titles;
import net.betterpvp.core.utility.UtilMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class AnnounceCommand extends Command {

    public AnnounceCommand() {
        super("announce", new String[]{"broadcast", "say"}, Rank.ADMIN);
    }

    @Override
    public void execute(Player player, String[] args) {
        if (args == null || args.length == 0) {
            UtilMessage.message(player, "Message", "Player argument missing.");
            return;
        }

        if (args.length >= 1) {
            String msg = UtilMessage.getFinalArg(args, 0);
            Bukkit.broadcastMessage(ChatColor.AQUA.toString() + ChatColor.BOLD + player.getName() + "> " + ChatColor.WHITE.toString() + ChatColor.BOLD + msg);
            Titles.sendTitle(player, 0, 40, 20, ChatColor.YELLOW + "Announce", msg);
        }
    }

    @Override
    public void help(Player player) {

    }

}
