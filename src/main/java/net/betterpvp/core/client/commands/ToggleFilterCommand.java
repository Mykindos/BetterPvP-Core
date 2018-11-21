package net.betterpvp.core.client.commands;

import org.bukkit.entity.Player;

import net.betterpvp.core.client.Rank;
import net.betterpvp.core.command.Command;

public class ToggleFilterCommand extends Command{

	public ToggleFilterCommand() {
		super("togglefilter", new String[]{}, Rank.PLAYER);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(Player player, String[] args) {
	/*	Client c = ClientUtilities.getOnlineClient(player);
		if(c.getGamer().isFiltering()){
			c.getGamer().setFilter(false);
			GamerRepository.updateGamer(c.getGamer());
			UtilMessage.message(player, "Options", "Filter is now disabled");
		}else{
			c.getGamer().setFilter(true);
			GamerRepository.updateGamer(c.getGamer());
			UtilMessage.message(player, "Options", "Filter is now enabled");
		}
		*/
	}

	@Override
	public void help(Player player) {
	}

}
