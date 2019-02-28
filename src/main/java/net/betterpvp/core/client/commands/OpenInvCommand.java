package net.betterpvp.core.client.commands;

import net.betterpvp.core.client.Rank;
import net.betterpvp.core.command.Command;
import net.betterpvp.core.utility.UtilMessage;
import net.betterpvp.core.utility.UtilPlayer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class OpenInvCommand extends Command {

    public OpenInvCommand() {
        super("openinv", new String[]{"invsee"}, Rank.ADMIN);
    }

    @Override
    public void execute(Player player, String[] args) {
        if (args == null || args.length == 0) {
            help(player);
            return;
        }

        Player target = UtilPlayer.searchOnline(player, args[0], false);
        if (target == null) {
            UtilPlayer.searchOnline(player, args[0], true);
            return;
        }

        player.openInventory(target.getInventory());
        UtilMessage.message(player, "Inventory", "You opened " + ChatColor.YELLOW + target.getName() + "'s" + ChatColor.GRAY + " inventory.");
    }

    @Override
    public void help(Player player) {
        UtilMessage.message(player, "/openinv <player>", "Open a players inventory", Rank.ADMIN);
    }
}