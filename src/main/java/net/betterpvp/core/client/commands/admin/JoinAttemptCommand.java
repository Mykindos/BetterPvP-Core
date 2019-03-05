package net.betterpvp.core.client.commands.admin;

import net.betterpvp.core.client.Rank;
import net.betterpvp.core.client.listeners.ConnectionListener;
import net.betterpvp.core.command.Command;
import org.bukkit.entity.Player;

public class JoinAttemptCommand extends Command {

    public JoinAttemptCommand() {
        super("joinattempts", new String[]{}, Rank.ADMIN);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void execute(Player player, String[] args) {
        player.sendMessage(ConnectionListener.JOIN_ATTEMPTS.size() + "");
    }

    @Override
    public void help(Player player) {
    }


}
