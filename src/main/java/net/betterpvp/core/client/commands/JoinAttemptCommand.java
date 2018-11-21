package net.betterpvp.core.client.commands;

import org.bukkit.entity.Player;

import net.betterpvp.core.client.Rank;
import net.betterpvp.core.client.listeners.ConnectionListener;
import net.betterpvp.core.command.Command;

public class JoinAttemptCommand extends Command{

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
