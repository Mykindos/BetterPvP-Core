package net.betterpvp.core.client.commands;

import net.betterpvp.core.client.Rank;
import net.betterpvp.core.command.Command;
import net.betterpvp.core.utility.UtilMessage;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class RulesCommand extends Command {

    public RulesCommand() {
        super("rules", new String[]{"rule"}, Rank.PLAYER);
    }

    @Override
    public void execute(Player player, String[] args) {
        UtilMessage.message(player, ChatColor.GRAY + "You can view the server rules on our website: " + ChatColor.GREEN + "https://betterpvp.net/forum/threads/rulebook.17/");
        UtilMessage.message(player, ChatColor.GRAY + "Not knowing is not an excuse!");
    }

    @Override
    public void help(Player player) {

    }
}
