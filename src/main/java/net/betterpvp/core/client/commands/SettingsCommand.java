package net.betterpvp.core.client.commands;

import net.betterpvp.core.client.Rank;
import net.betterpvp.core.command.Command;
import org.bukkit.entity.Player;

public class SettingsCommand extends Command {

    public SettingsCommand() {
        super("settings", new String[]{"options"}, Rank.PLAYER);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void execute(Player player, String[] args) {
        //	player.openInventory(new PlayerSettingMenu(player).getInventory());
    }

    @Override
    public void help(Player player) {
    }

}
