package net.betterpvp.core.client.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.betterpvp.core.client.Rank;
import net.betterpvp.core.command.Command;
import net.betterpvp.core.utility.UtilMessage;

import net.md_5.bungee.api.ChatColor;

public class ListCommand extends Command{

	public ListCommand() {
		super("list", new String[]{}, Rank.PLAYER);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(Player player, String[] args) {
		int size = Bukkit.getOnlinePlayers().size();
		int authed = 0;
		
		List<String> players = new ArrayList<>();
		for(Player p : Bukkit.getOnlinePlayers()){
			
			if(OfflineCommand.offline.contains(p.getUniqueId())) continue;
			
			boolean mah = false;//MAHManager.isAuthenticated(p);
			if(mah){
				authed++;
			}
			players.add(mah ? ChatColor.GREEN + " * " + ChatColor.GRAY + p.getName() + ChatColor.YELLOW : ChatColor.GRAY + " " +  p.getName() + ChatColor.YELLOW);
			
		}
		UtilMessage.message(player, "List", "There are currently " + ChatColor.YELLOW + size + ChatColor.GRAY + " players online.");
		UtilMessage.message(player, "List", "There are currently " + ChatColor.YELLOW + authed + ChatColor.GRAY + " authenticated players.");
		UtilMessage.message(player, "List", ChatColor.YELLOW + players.toString());
		
		
		
	}

	@Override
	public void help(Player player) {
		// TODO Auto-generated method stub
		
	}

}
