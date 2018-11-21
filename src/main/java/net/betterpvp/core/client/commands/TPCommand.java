package net.betterpvp.core.client.commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.betterpvp.core.client.Rank;
import net.betterpvp.core.command.Command;

public class TPCommand extends Command{

	public TPCommand() {
		super("tp", new String[]{}, Rank.ADMIN);
	}

	@Override
	public void execute(Player player, String[] args) {
		if(args.length == 1){
			Player p = Bukkit.getPlayer(args[0]);
			if(p != null){
				player.teleport(p.getLocation());
			}
		}

	}

	@Override
	public void help(Player player) {
		// TODO Auto-generated method stub

	}
}
