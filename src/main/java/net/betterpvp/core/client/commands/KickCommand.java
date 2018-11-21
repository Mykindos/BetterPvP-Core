package net.betterpvp.core.client.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import net.betterpvp.core.client.Rank;
import net.betterpvp.core.command.Command;
import net.betterpvp.core.utility.UtilMessage;
import net.betterpvp.core.utility.UtilPlayer;

public class KickCommand extends Command {

	public KickCommand() {
		super("kick", new String[]{}, Rank.MODERATOR);
	}

	@Override
	public void execute(Player player, String[] args) {
		if (args == null || args.length == 0) {
			UtilMessage.message(player, "Kick", "You did not input a Player to kick.");
			return;
		}

		Player target = UtilPlayer.searchOnline(player, args[0], false);
		if (args.length == 1) {
			if (target == null) {
				UtilPlayer.searchOnline(player, args[0], true);
				return;
			}

			UtilMessage.message(player, "Kick", "You did not input a reason.");
			return;
		}

		String reason = UtilMessage.getFinalArg(args, 1);
		if(target != null){
			target.kickPlayer(ChatColor.RED + "[Kick] " + ChatColor.GRAY + reason);
			UtilMessage.broadcast("Kick", ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " kicked " + ChatColor.YELLOW + target.getName() + ChatColor.GRAY + " for "
					+ ChatColor.GREEN + reason);
		}
	}




		@Override
		public void help(Player player) {

		}

	}
