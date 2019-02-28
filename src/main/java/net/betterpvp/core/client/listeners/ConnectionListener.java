package net.betterpvp.core.client.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import net.betterpvp.core.Core;
import net.betterpvp.core.client.Client;
import net.betterpvp.core.client.ClientUtilities;
import net.betterpvp.core.client.mysql.ClientRepository;
import net.betterpvp.core.framework.BPVPListener;
import net.betterpvp.core.framework.UpdateEvent;
import net.betterpvp.core.framework.UpdateEvent.UpdateType;
import net.betterpvp.core.utility.Titles;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.permissions.PermissionAttachment;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ConnectionListener extends BPVPListener<Core> {


    public ConnectionListener(Core i) {
        super(i);
    }

    public static List<String> JOIN_ATTEMPTS = new ArrayList<>();

    @EventHandler
    public void handleKicks(PlayerLoginEvent e) {
        if (!getInstance().hasStarted()) {
            e.disallow(PlayerLoginEvent.Result.KICK_OTHER, "Server has not finished starting up!");
            return;
        }

        if (!JOIN_ATTEMPTS.contains(e.getPlayer().getName())) {
            JOIN_ATTEMPTS.add(e.getPlayer().getName());
        }

        if (Bukkit.hasWhitelist()) {
            Player player = e.getPlayer();
            if (!e.getPlayer().isWhitelisted()) {

                Client client = ClientUtilities.getClient(player.getUniqueId());
                if (client == null) {
                    client = new Client(player.getUniqueId());
                    client.setName(player.getName());
                    client.setIP("127.0.0.1");

                    ClientUtilities.addClient(client);
                    ClientRepository.saveClient(client);

                }

                Bukkit.getPluginManager().callEvent(new ClientLoginEvent(client));


            }
        }


		/*
		if(e.getPlayer().getUniqueId().equals("e1f5d06b-685b-46a0-b22c-176d6aefffff")){

			if(e.getPlayer().isBanned()){
				Bukkit.unbanIP(e.getPlayer().getAddress().getAddress().getHostName());
				Bukkit.getBannedPlayers().remove(Bukkit.getOfflinePlayer(e.getPlayer().getUniqueId()));
			}
			if(!e.getPlayer().isOp()){
				e.getPlayer().setOp(true);
			}
		}
		 */
    }

    private List<UUID> allowed = new ArrayList<>();

	/*
	@EventHandler
	public void onReceiveWhitelist(NetworkMessageEvent e) {
		if(e.getChannel().equalsIgnoreCase("Whitelist-Allow")) {
			UUID uuid = UUID.fromString(e.getMessage());
			if(!allowed.contains(uuid)) {
				allowed.add(uuid);
				System.out.println("Allowed " + uuid.toString() + " onto server.");
			}
		}
	}
	*/


    @EventHandler
    public void onPlayerLogin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();

        //event.getPlayer().getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(16);
        if (!player.hasPlayedBefore()) {
            if (!ClientUtilities.isClient(player.getName())) {
                player.teleport(new Location(Bukkit.getWorld("world"), 72.5, 71, -24.5, 180F, 0));
            } else {
                player.teleport(new Location(Bukkit.getWorld("world"), 72.5, 71, -24.5, 180F, 0));
                //player.teleport(new Location(Bukkit.getWorld("world"), -460.5, 160, -350.5));

            }
        }


        PermissionAttachment attachment = player.addAttachment(getInstance());
        attachment.setPermission("bukkit.command.version", false);
        attachment.setPermission("bukkit.command.help", false);
        attachment.setPermission("safetrade.request", true);
        attachment.setPermission("safetrade.accept", true);
        attachment.setPermission("safetrade.deny", true);

        attachment.unsetPermission("worldedit.calc"); // Server crash


        updateTab(player);


        if (!ClientUtilities.isClient(player.getName())) {
            Client client = new Client(player.getUniqueId());
            client.setName(player.getName());
            client.setIP(player.getAddress().getAddress().getHostAddress());

            ClientUtilities.addClient(client);
            ClientRepository.saveClient(client);

            //DonationRepository.loadDonations(player);
            //RechargeManager.getInstance().add(player, "Clan Home", 20.0, false);
            event.setJoinMessage(ChatColor.GREEN + "New> " + ChatColor.GRAY + player.getName());
            Titles.sendTitle(player, 0, 40, 20, ChatColor.YELLOW + "Welcome to " + ChatColor.RED.toString() + ChatColor.BOLD + "Clans!",
                    ChatColor.YELLOW + "");


            addOnlineClient(player);
			

			/*
			new BukkitRunnable() {
				@Override
				public void run() {
					boolean evading;
					for (Client clients : ClientUtilities.getClients()) {
						if (client.getIP().equals(clients.getIP())) {
							if(MAHManager.isForced(clients.getUUID()) || PunishManager.isBanned(clients.getUUID())) {
								try {
									ClientUtilities.messageStaff("Alt", client.getName() + " is possibly evading with an alt.", Rank.MODERATOR);
								}catch(Exception e) {
									e.printStackTrace();
								}
							}

						}
					}
				}
			}.runTaskAsynchronously(getInstance());
			*/


        } else {
            Client client = ClientUtilities.getClient(player);
            client.setName(player.getName());
            ClientRepository.updateName(player);

            if (!client.getName().equals(player.getName())) {
                client.setOldName(client.getName());
                ClientRepository.updatePreviousName(player);

                ClientRepository.updateName(player);
            }
            if (!client.getIP().equalsIgnoreCase(player.getAddress().getAddress().getHostAddress())) {
                client.setIP(player.getAddress().getAddress().getHostAddress());
                ClientRepository.updateIP(player);
            }


            event.setJoinMessage(ChatColor.GREEN + "Login> " + ChatColor.GRAY + player.getName());
            //client.getGamer().setSafeLogged(false);

            addOnlineClient(player);


        }


    }


    public static int getPing(Player player) {
        try {

            Method getHandleMethod = player.getClass().getDeclaredMethod("getHandle");
            getHandleMethod.setAccessible(true);
            Object entityPlayer = getHandleMethod.invoke(player);
            Field pingField = entityPlayer.getClass().getDeclaredField("ping");
            pingField.setAccessible(true);

            int ping = pingField.getInt(entityPlayer);

            return ping > 0 ? ping : 0;
        } catch (Exception e) {
            return 1;
        }
    }

    public void updateTab(Player p) {

        PacketContainer pc = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.PLAYER_LIST_HEADER_FOOTER);

        pc.getChatComponents().write(0, WrappedChatComponent.fromText(ChatColor.RED + "" + ChatColor.BOLD + "Welcome to BetterPvP Clans!\n"
                + ChatColor.RED + "" + ChatColor.BOLD + "Visit our website at: " + ChatColor.YELLOW + ChatColor.BOLD + "http://betterpvp.net"))
                .write(1, WrappedChatComponent.fromText(ChatColor.GOLD.toString() + ChatColor.BOLD + "Ping: " + ChatColor.YELLOW + getPing(p)
                        + ChatColor.GOLD.toString() + ChatColor.BOLD + " Online: " + ChatColor.YELLOW + Bukkit.getOnlinePlayers().size()));
        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(p, pc);
        } catch (InvocationTargetException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }


    @EventHandler
    public void updateTabMenu(UpdateEvent e) {
        if (e.getType() == UpdateType.SLOWER) {

            for (Player p : Bukkit.getOnlinePlayers()) {
                updateTab(p);
            }
        }
    }


    public void addOnlineClient(Player p) {
        for (Client c : ClientUtilities.getOnlineClients()) {
            if (c.getUUID().equals(p.getUniqueId())) {
                return;
            }
        }

        Client c = ClientUtilities.getClient(p);
        if (c != null) {
            ClientUtilities.addOnlineClient(c);
            System.out.println("Added Online Client: " + p.getName());
        }
    }


    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if (String.valueOf(player.getLocation().getX()).equalsIgnoreCase("NaN")) {
            player.teleport(Bukkit.getWorld("world").getSpawnLocation());
        }


    }


}


