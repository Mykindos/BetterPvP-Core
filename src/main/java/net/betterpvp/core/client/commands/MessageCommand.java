package net.betterpvp.core.client.commands;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import net.betterpvp.core.client.Client;
import net.betterpvp.core.client.ClientUtilities;
import net.betterpvp.core.client.Rank;
import net.betterpvp.core.command.Command;
import net.betterpvp.core.punish.PunishManager;
import net.betterpvp.core.utility.UtilMessage;
import net.betterpvp.core.utility.UtilPlayer;

public class MessageCommand extends Command {

	public static HashMap<String, String> messages = new HashMap<>();

	public MessageCommand() {
		super("m", new String[]{"msg", "tell", "message"}, Rank.PLAYER);
	}

	@Override
	public void execute(Player player, String[] args) {
		if (args == null || args.length == 0) {
			UtilMessage.message(player, "Message", "Player argument missing.");
			return;
		}

		if(PunishManager.isMuted(player.getUniqueId())){
			UtilMessage.message(player, "Punish", "You cannot private message while muted!");
			return;
		}

		Player target = UtilPlayer.searchOnline(player, args[0], false);

		if (target == null) {
			UtilPlayer.searchOnline(player, args[0], true);
			return;
		}
		Client tClient = ClientUtilities.getOnlineClient(target);
		if (args.length == 1) {
			UtilMessage.message(player, "Message", "Message argument missing.");
			return;
		}

		if (args.length >= 2) {
			String msg = UtilMessage.getFinalArg(args, 1);

			if (ClientUtilities.getOnlineClient(player).getIgnore().contains(target.getUniqueId())) {
				UtilMessage.message(player, "Ignore", "You are ignonring " + ChatColor.YELLOW + target.getName() + ChatColor.GRAY + ".");
				return;
			}

			if (ClientUtilities.getOnlineClient(target).getIgnore().contains(player.getUniqueId())) {
				UtilMessage.message(player, ChatColor.DARK_AQUA + "[" + ChatColor.AQUA + "You" + ChatColor.DARK_AQUA + " -> " + ChatColor.AQUA + target.getName() + ChatColor.DARK_AQUA + "] " + ChatColor.GRAY + msg);
				return;
			}

			
				for (Player online : Bukkit.getOnlinePlayers()) {
					if(ClientUtilities.getOnlineClient(online) != null){
						if (ClientUtilities.getOnlineClient(online).isAdministrating()) {
							if (player.equals(online) || online.equals(player)) {
								continue;
							}

							UtilMessage.message(online, ChatColor.DARK_GREEN + "[" + ChatColor.GREEN + player.getName() + ChatColor.DARK_GREEN + " -> " + ChatColor.GREEN + target.getName() + ChatColor.DARK_GREEN + "] " + ChatColor.GRAY + msg);
						}
					}
				}



				UtilMessage.message(player, ChatColor.DARK_AQUA + "[" + ChatColor.AQUA + "You" + ChatColor.DARK_AQUA + " -> " + ChatColor.AQUA + target.getName() + ChatColor.DARK_AQUA + "] " + ChatColor.GRAY + msg);

				UtilMessage.message(target, ChatColor.DARK_AQUA + "[" + ChatColor.AQUA + player.getName() + ChatColor.DARK_AQUA + " -> " + ChatColor.AQUA + "You" + ChatColor.DARK_AQUA + "] " + ChatColor.GRAY + msg);
				messages.put(target.getName(), player.getName());
				messages.put(player.getName(), target.getName());
			
		}
	}

	@Override
	public void help(Player player) {

	}
}
