package net.betterpvp.core.client.commands;

import net.betterpvp.core.client.Rank;
import net.betterpvp.core.command.Command;
import net.betterpvp.core.utility.UtilMessage;
import org.bukkit.entity.Player;

public class PlayerCapToggleCommand extends Command {

    public static boolean capPlayers = true;

    public PlayerCapToggleCommand() {
        super("playercap", new String[]{}, Rank.OWNER);
        // TODO Auto-generated constructor stub
    }


    @Override
    public void execute(Player player, String[] args) {
        capPlayers = !capPlayers;
        UtilMessage.message(player, "Server", "Player cap restricted: " + capPlayers);
    }

    @Override
    public void help(Player player) {
    }

}
