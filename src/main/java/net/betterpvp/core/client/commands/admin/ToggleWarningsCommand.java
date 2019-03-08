package net.betterpvp.core.client.commands.admin;

import net.betterpvp.core.client.Client;
import net.betterpvp.core.client.ClientUtilities;
import net.betterpvp.core.client.Rank;
import net.betterpvp.core.command.Command;
import net.betterpvp.core.utility.UtilMessage;
import org.bukkit.entity.Player;

public class ToggleWarningsCommand extends Command {

    public ToggleWarningsCommand() {
        super("togglewarnings", new String[]{}, Rank.ADMIN);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void execute(Player player, String[] args) {

    }

    @Override
    public void help(Player player) {
    }

}
