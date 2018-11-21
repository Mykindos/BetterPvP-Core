package net.betterpvp.core.client.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import net.betterpvp.core.client.Rank;
import net.betterpvp.core.command.Command;
import net.betterpvp.core.utility.UtilMessage;

public class OfflineCommand extends Command{

	public static List<UUID> offline = new ArrayList<>();
	
	public OfflineCommand() {
		super("offline", new String[]{}, Rank.ADMIN);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(Player player, String[] args) {
		if(offline.contains(player.getUniqueId())){
			offline.remove(player.getUniqueId());
			Bukkit.broadcastMessage(ChatColor.GREEN + "Login> " + ChatColor.GRAY + player.getName());
			UtilMessage.message(player, "Admin", "You are no longer appearing offline");
			for(Player p : Bukkit.getOnlinePlayers()){
				p.showPlayer(player);
			}
		}else{
			offline.add(player.getUniqueId());
			UtilMessage.message(player, "Admin", "You are now appearing offline");
			Bukkit.broadcastMessage(ChatColor.RED + "Leave> " + ChatColor.GRAY 
					+ player.getName() + " (" + ChatColor.GREEN + "Safe" + ChatColor.GRAY + ")");
			for(Player p : Bukkit.getOnlinePlayers()){
				p.hidePlayer(player);
			}
		}
	}

	@Override
	public void help(Player player) {
	}
	
	

}
