package net.betterpvp.core.client.commands;

import org.bukkit.entity.Player;

import net.betterpvp.core.client.Rank;
import net.betterpvp.core.command.Command;
import net.betterpvp.core.utility.UtilMessage;
import net.md_5.bungee.api.ChatColor;

public class VoteCommand extends Command{

	public VoteCommand() {
        super("Vote", new String[]{}, Rank.PLAYER);
    }

	@Override
	public void execute(Player player, String[] args) {
		UtilMessage.message(player, "Vote", "Link: " + ChatColor.AQUA + "http://betterpvp.net/vote.html");
		
	}

	@Override
	public void help(Player player) {
		// TODO Auto-generated method stub
		
	}
}
