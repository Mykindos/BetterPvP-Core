package net.betterpvp.core.client.commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.betterpvp.core.client.Rank;
import net.betterpvp.core.command.Command;
import net.betterpvp.core.utility.UtilVelocity;

public class KnockbackTestCommand extends Command{

	public KnockbackTestCommand() {
		super("knockback", new String[]{}, Rank.ADMIN);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(Player player, String[] args) {
		if(args != null){
			if(args.length == 1){
				Player p = Bukkit.getPlayer(args[0]);
				if(p != null){
					UtilVelocity.velocity(p, player.getLocation().getDirection(), 5, false, 1, 1, 1, false);
				}
			}
		}
	}

	@Override
	public void help(Player player) {
	}
	
	

}
