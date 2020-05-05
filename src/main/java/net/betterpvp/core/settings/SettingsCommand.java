package net.betterpvp.core.settings;

import net.betterpvp.core.client.Rank;
import net.betterpvp.core.command.Command;
import net.betterpvp.core.settings.menu.MainSettingsMenu;
import org.bukkit.entity.Player;

public class SettingsCommand extends Command {

    public SettingsCommand() {
        super("settings", new String[]{"options", "prefs"}, Rank.PLAYER);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void execute(Player player, String[] args) {
        	player.openInventory(new MainSettingsMenu(player).getInventory());
    }

    @Override
    public void help(Player player) {
    }

}
