package net.betterpvp.core.client.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.betterpvp.core.client.Client;
import net.betterpvp.core.client.ClientUtilities;
import net.betterpvp.core.database.Log;
import net.betterpvp.core.punish.Punish;
import net.betterpvp.core.punish.Punish.PunishType;
import net.betterpvp.core.punish.PunishManager;
import net.betterpvp.core.punish.mysql.PunishRepository;

public class AutoUnbanCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {

		if(cmd.getName().equalsIgnoreCase("unban")){
			if(!(sender instanceof Player)){
				if(args.length == 1){
					Client c = ClientUtilities.getClient(args[0]);
					if(c != null){
						if(PunishManager.isBanned(c.getUUID())){
							Punish p = PunishManager.getPunish(c.getUUID(),	 PunishType.BAN);
							PunishRepository.removePunishment(p);
							PunishManager.removePunishment(p);

							Log.write("Shop Unban", c.getName() + " bought an unban");
						}
					}
				}
			}
		}
		return true;
	}

}
