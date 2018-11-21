package net.betterpvp.core.client.commands.admin;

import org.bukkit.entity.Player;

import net.betterpvp.core.client.Client;
import net.betterpvp.core.client.Rank;
import net.betterpvp.core.command.Command;
import net.betterpvp.core.utility.UtilMessage;
import net.betterpvp.core.client.ClientUtilities;

public class ToggleWarningsCommand extends Command{

	public ToggleWarningsCommand() {
		super("togglewarnings", new String[] {}, Rank.ADMIN);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(Player player, String[] args) {
		Client c = ClientUtilities.getOnlineClient(player);
		if(c != null) {
			c.setShowWarnings(!c.isShowingWarnings());
			UtilMessage.message(player, "Settings", "Show Warnings: " + c.isShowingWarnings());
		}
	}

	@Override
	public void help(Player player) {
	}

}
