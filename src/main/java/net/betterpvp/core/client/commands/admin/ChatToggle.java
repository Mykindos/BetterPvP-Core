package net.betterpvp.core.client.commands.admin;


import net.betterpvp.core.client.Rank;
import net.betterpvp.core.command.Command;
import net.betterpvp.core.utility.UtilMessage;
import org.bukkit.entity.Player;

public class ChatToggle extends Command {

    public static boolean enabled = true;

    public ChatToggle() {
        super("togglechat", new String[]{}, Rank.ADMIN);
    }

    @Override
    public void execute(Player player, String[] args) {
        if (enabled) {
            enabled = false;
            UtilMessage.broadcast("Server", "Chat has been disabled!");
        } else {
            UtilMessage.broadcast("Server", "Chat has been enabled!");
            enabled = true;
        }

    }

    @Override
    public void help(Player player) {
        // TODO Auto-generated method stub

    }
}
