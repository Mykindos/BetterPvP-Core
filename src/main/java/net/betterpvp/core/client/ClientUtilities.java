package net.betterpvp.core.client;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import net.betterpvp.core.utility.fancymessage.FancyMessage;
import net.betterpvp.core.punish.PunishManager;
import net.betterpvp.core.utility.UtilMessage;


public class ClientUtilities {

	private static List<Client> clients = new ArrayList<>();
	private static List<Client> onlineClients = new ArrayList<>();

	public static List<Client> getClients(){
		return clients;
	}

	public static List<Client> getOnlineClients(){
		return onlineClients;
	}

	public static void addOnlineClient(Client c){
		onlineClients.add(c);


	}

	public static Client getOnlineClient(UUID uuid){
		for (Client client : onlineClients) {

			if (client.getUUID().equals(uuid)) {
				return client;
			}
		}
		return null;
	}

	public static Client getOnlineClient(Player p){
		return getOnlineClient(p.getUniqueId());
	}

	public static void addClient(Client c){
		clients.removeIf(x -> x.getUUID().toString().equalsIgnoreCase(c.getUUID().toString()));
		onlineClients.removeIf(x -> x.getUUID().toString().equalsIgnoreCase(c.getUUID().toString()));
		clients.add(c);

		if(Bukkit.getPlayer(c.getUUID()) != null) {
			onlineClients.add(c);
		}
	}

	public static void addClientOnLoad(Client c){

		clients.add(c);


	}

	public static Client getClient(UUID uuid) {
		for (Client client : clients) {
			if (client.getUUID().equals(uuid)) {
				return client;
			}
		}
		return null;
	}

	public static Client getClient(String string) {
		for (Client client : clients) {
			if (client.getName().equalsIgnoreCase(string)
					|| client.getUUID().toString().equalsIgnoreCase(string)) {
				return client;
			}
		}
		return null;
	}

	public static Client getOnlineClient(String string) {
		for (Client client : onlineClients) {
			if (client.getName().equalsIgnoreCase(string)
					|| client.getUUID().toString().equalsIgnoreCase(string)) {
				return client;
			}
		}
		return null;
	}

	public static Client getClient(Player player) {
		Client uuid = getClient(player.getUniqueId());
		if(uuid != null){
			return uuid;
		}

		Client name = getClient(player.getName());
		if (name != null) {
			return name;
		}

		return null;
	}

	public static boolean isClient(String string) {
		if (getClient(string) != null) {
			return true;
		}

		if (Bukkit.getPlayer(string) != null) {
            return getClient(Bukkit.getPlayer(string)) != null;
		}

		return false;
	}


	public static boolean isClient(UUID uuid) {
        return getClient(uuid) != null;


    }


	public static Client searchClient(Player caller, String client, boolean inform) {
		LinkedList<Client> matchList = new LinkedList<>();
		for (Client cur : clients) {
			if (cur.getName().equalsIgnoreCase(client)) {
				return cur;
			}
			if (cur.getName().toLowerCase().contains(client.toLowerCase())) {
				matchList.add(cur);
			}
		}
		if (matchList.size() != 1) {
			if (!inform) {
				return null;
			}

			UtilMessage.message(caller, "Client Search", ChatColor.YELLOW.toString() + matchList.size() + ChatColor.GRAY
					+ " matches for [" + ChatColor.YELLOW + client + ChatColor.GRAY + "].");

							if (matchList.size() > 0) {
								String matchString = "";
								for (Client cur : matchList) {
									matchString = matchString + ChatColor.YELLOW + cur.getName() + ChatColor.GRAY + ", ";
								}
								if (matchString.length() > 1) {
									matchString = matchString.substring(0, matchString.length() - 2);
								}
								UtilMessage.message(caller, "Client Search", ChatColor.GRAY + "Matches [" + ChatColor.YELLOW + matchString + ChatColor.GRAY + "].");
							}
							return null;
		}
		return matchList.get(0);
	}

	public static void messageStaff(String prefix, String message, Rank rank) {
		messageStaffSound(prefix,message,null, rank);
	}

	public static void messageStaff(String message, Rank r) {
		for(Player p : Bukkit.getOnlinePlayers()){
			Client c = ClientUtilities.getOnlineClient(p);
			if (c.hasRank(r, false)) {
				if (Bukkit.getPlayer(c.getUUID()) != null) {
					UtilMessage.message(Bukkit.getPlayer(c.getUUID()), message);

				}
			}
		}
	}
	
	public static void messageStaffTarget(String prefix, String message, Player target) {
		if(prefix.contains("Anti-Hack")) {
			 for(Player p : Bukkit.getOnlinePlayers()){
		         	Client c = ClientUtilities.getOnlineClient(p);
		             if (c.hasRank(Rank.ADMIN, false)) {
		                 if (Bukkit.getPlayer(c.getUUID()) != null) {
		                     
		                	 new FancyMessage(ChatColor.BLUE + prefix + "> " + message).then(" " + ChatColor.RED.toString() + ChatColor.UNDERLINE + "Force")
		                	 .command("/mah add " + target.getName() + " 7 d").send(p);
                         }
		             }
		         }
		}else {
			messageStaffSound(prefix, message, null, Rank.ADMIN);
		}
	}

	public static void messageStaff(String prefix, String message) {
		
			messageStaffSound(prefix, message, null, Rank.ADMIN);
		
	}

	public static void messageStaffSound(String prefix, String message, Sound sound, Rank rank){
		for(Player p : Bukkit.getOnlinePlayers()){
			Client c = ClientUtilities.getOnlineClient(p);
			if (c.hasRank(rank, false)) {
				if (Bukkit.getPlayer(c.getUUID()) != null) {
					UtilMessage.message(Bukkit.getPlayer(c.getUUID()), prefix, message);
					if(sound != null){
						p.playSound(p.getLocation(), sound, 1.0f, 1.0f);
					}
				}
			}
		}
	}

	public static boolean isStaffOnline(Rank minRank){
		for(Player p : Bukkit.getOnlinePlayers()){
			if(ClientUtilities.getClient(p).hasRank(minRank, false)){
				return true;
			}
		}
		return false;
	}

	public static void messageStaff(String prefix, String message, Player ignore) {
		for (Client client : clients) {
			if (client.hasRank(Rank.MODERATOR, false)) {
				if (Bukkit.getPlayer(client.getUUID()) != null) {
					if (Bukkit.getPlayer(client.getUUID()).equals(ignore)) {
						continue;
					}

					UtilMessage.message(Bukkit.getPlayer(client.getUUID()), prefix, message);
				}
			}
		}
	}

	public static String getIPAlias(Client client) {
		StringBuilder alias = new StringBuilder();

		for (Client clients : clients) {
			if (client.getIP().equals(clients.getIP())) {
				if (clients.getUUID().equals(client.getUUID())) {
					continue;
				}
				//  String name = PunishManager.isBanned(clients.getUUID()) ? ChatColor.RED
				alias.append(alias.length() != 0 ? ChatColor.DARK_GRAY + ", " : "")
				.append(PunishManager.isBanned(clients.getUUID()) ? ChatColor.RED + clients.getName() :
					true/*MAHManager.isForced(clients.getUUID())*/ ? ChatColor.AQUA + clients.getName() : ChatColor.GRAY + clients.getName());
			}
		}
		return alias.toString();
	}

	public static String getDetailedIPAlias(Client client) {
		StringBuilder alias = new StringBuilder();

		for (Client clients : clients) {
			if (client.getIP().equals(clients.getIP())) {
				if (clients.getUUID().equals(client.getUUID())) {
					continue;
				}
				//  String name = PunishManager.isBanned(clients.getUUID()) ? ChatColor.RED
				alias.append(alias.length() != 0 ? ChatColor.DARK_GRAY + ", " : "")
				.append(PunishManager.isBanned(clients.getUUID()) ? ChatColor.RED + clients.getName() :
					/*MAHManager.isForced(clients.getUUID())*/true ? ChatColor.AQUA + clients.getName() : ChatColor.GRAY + clients.getName())
				.append(clients.isDiscordLinked() ? ChatColor.WHITE + " (L)" : "");
			}
		}
		return alias.toString();
	}
}
