package net.betterpvp.core.client.commands;

import net.betterpvp.core.client.Rank;
import net.betterpvp.core.command.Command;
import net.betterpvp.core.utility.UtilMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ClearChatCommand extends Command {

    public ClearChatCommand() {
        super("clearchat", new String[]{}, Rank.ADMIN);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void execute(Player player, String[] args) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            for (int i = 0; i < 100; i++) {
                UtilMessage.message(p, " ");
            }
        }
    }

    @Override
    public void help(Player player) {
    }

}
